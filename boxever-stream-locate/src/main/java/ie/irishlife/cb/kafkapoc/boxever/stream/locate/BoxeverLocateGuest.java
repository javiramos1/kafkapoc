package ie.irishlife.cb.kafkapoc.boxever.stream.locate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import ie.irishlife.cb.kafkapoc.boxever.api.model.boxever.Guest;
import ie.irishlife.cb.kafkapoc.boxever.api.model.boxever.GuestWrapper;
import ie.irishlife.cb.kafkapoc.boxever.api.serializer.GuestSerde;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Properties;

import static ie.irishlife.cb.kafkapoc.boxever.api.constants.KafkaConstants.*;

/**
 * Stream App that uses Boxever API to locate a guest and perform
 * updates on the Guest model creating a result object that is ready to update.
 *
 *  * USE: http://www.jsonschema2pojo.org/ for mapping JSON
 *  *
 *  * TODO: Use Avro + Schema Registry instead of JSON
 *
 */
public class BoxeverLocateGuest {

    private final static Logger LOG = Logger.getLogger(BoxeverLocateGuest.class);

    private static final String API_KEY = System.getenv("API_KEY") == null  ?
            "" : System.getenv("API_KEY");
    private static final String API_SECRET = System.getenv("API_SECRET") == null  ?
            "" : System.getenv("API_SECRET");;
    private static final String BASE_URL = "https://api.boxever.com/v2";

    private static final String  GUEST = "/guests";

    /**
     * Setup consumer. Uses env variables for docker.
     * @return Properties
     */
    private static Properties setUpStreamProperties() {
        Properties config = new Properties();

        config.put(StreamsConfig.APPLICATION_ID_CONFIG, System.getenv(KAFKA_STREAM_APP) == null
                ? "boxever-stream-locate" : System.getenv(KAFKA_STREAM_APP) );

        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, System.getenv(KAFKA_BROKER) == null
                ? DEFAULT_BROKER : System.getenv(KAFKA_BROKER));

        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, GuestSerde.class);

        config.put(
                StreamsConfig.PRODUCER_PREFIX + ProducerConfig.INTERCEPTOR_CLASSES_CONFIG,
                "io.confluent.monitoring.clients.interceptor.MonitoringProducerInterceptor");

        config.put(
                StreamsConfig.CONSUMER_PREFIX + ConsumerConfig.INTERCEPTOR_CLASSES_CONFIG,
                "io.confluent.monitoring.clients.interceptor.MonitoringConsumerInterceptor");
        return config;
    }

    private static class SetUpErrorTopic {
        private static Producer<String, String> invoke() {
            Properties producerConfig = new Properties();
            producerConfig.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, System.getenv(KAFKA_BROKER) == null
                    ? DEFAULT_BROKER : System.getenv(KAFKA_BROKER));

            producerConfig.put(ProducerConfig.ACKS_CONFIG, "all");
            producerConfig.put(ProducerConfig.RETRIES_CONFIG, 0);
            return new KafkaProducer<>(producerConfig, new StringSerializer(),  new StringSerializer());
        }
    }


    /**
     * Main Method.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args)  {

        LOG.info("App Starting...");

        Properties config = setUpStreamProperties();

        LOG.info(" Kafka Properties set: " + config);

        final Producer<String, String> errorProducer = SetUpErrorTopic.invoke();

        LOG.info(" errorProducer initialized: " + errorProducer);

        StreamsBuilder builder = new StreamsBuilder();

        setJsonMapper();// set the HTTP mapping to read the object back from Boxever

        KStream<String, GuestWrapper> rawData = builder.stream(System.getenv(KAFKA_TOPIC) == null
                ? "boxever-locate"  : System.getenv(KAFKA_TOPIC) );

        rawData.mapValues( val ->  locateGuest(errorProducer, val)); // set href

        rawData.to( "boxever-consume");

        LOG.info(" KStream initialized: " + rawData);

        final KafkaStreams streams = new KafkaStreams(builder.build(), config);
        streams.start();

        LOG.info(" KStream started: " + streams);

        // shutdown hook to correctly close the streams application
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOG.warn(" BoxeverLocateGuest stopped ");
            streams.close();
            errorProducer.close();
            LOG.warn(" BoxeverLocateGuest resources closed ");
        }));

    }

    /**
     * HTTP JSON  body mapper
     */
    private static void setJsonMapper(){
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public <GuestWrapper> GuestWrapper readValue(String value, Class<GuestWrapper> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }



    /**
     * Find href for customer based on email
     * @param producer error producer to send errors to
     * @param guest GuestWrapper
     * @return GuestWrapper updated object
     */
    private static GuestWrapper locateGuest(Producer<String, String> producer, GuestWrapper guest) {
        try {
            LOG.info("locateGuest: " + guest);

            GuestWrapper retVal = null;

            if (guest != null & guest.getGuest() != null){
                final String email = guest.getGuest().getEmails().get(0);

                final String href = getGuestHref(email);
                guest.setHref(href);// set the href

                //get and update model
                setGuestObj(guest, href);

                retVal =  guest;
            }

            return retVal;

        }catch (Exception e) {
            // log + ignore/skip the corrupted message
            LOG.error("Could not deserialize record: " + e.getMessage());
            producer.send(new ProducerRecord<String, String>("boxever-error", (guest == null ? "Null" : guest.toString()), e.getMessage()));

        }

        return null;
    }

    /**
     * Update Member from Boxever with the CDC data. Call Boxever GET guest.
     * @param guest
     * @param href
     * @throws UnirestException
     */
    private static void setGuestObj(GuestWrapper guest, String href) throws UnirestException {
        // HTTP Call
        final HttpResponse<Guest> getResponse = Unirest.get(href)
                .basicAuth(API_KEY, API_SECRET)
                .asObject(Guest.class);

        final Guest boxeverGuest = getResponse.getBody();//current in Boxever
        final Guest ilGuest = guest.getGuest();// the one from CDC

        //TODO: Map more values
        boxeverGuest.setFirstName(ilGuest.getFirstName() == null ?
                boxeverGuest.getFirstName() : ilGuest.getFirstName());
        boxeverGuest.setLastName(ilGuest.getLastName() == null ?
                boxeverGuest.getLastName() : ilGuest.getLastName());
        boxeverGuest.setDateOfBirth(ilGuest.getDateOfBirth() == null ?
                boxeverGuest.getDateOfBirth() : ilGuest.getDateOfBirth());
        boxeverGuest.setEmails(ilGuest.getEmails() == null ?
                boxeverGuest.getEmails() : ilGuest.getEmails());
        boxeverGuest.setStreet(ilGuest.getStreet() == null ?
                boxeverGuest.getStreet() : ilGuest.getStreet());

        guest.setGuest(boxeverGuest);
    }

    /**
     * Call Boxever Locate REST end point
     * @param email
     * @return
     * @throws UnirestException
     */
    private static String getGuestHref(String email) throws UnirestException {
        HttpResponse<JsonNode> jsonResponse = Unirest.get(BASE_URL + GUEST + "?email=" + email).
                basicAuth(API_KEY, API_SECRET).
                asJson();
        LOG.info("jsonResponse: " + jsonResponse);

        final JSONObject refObj = (JSONObject)jsonResponse.getBody().getObject().getJSONArray("items").get(0);
        return refObj.getString("href");
    }


}
