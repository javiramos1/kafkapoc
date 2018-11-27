package ie.irishlife.cb.kafkapoc.boxever.stream.locate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import ie.irishlife.cb.kafkapoc.boxever.api.model.Guest;
import ie.irishlife.cb.kafkapoc.boxever.api.model.GuestWrapper;
import ie.irishlife.cb.kafkapoc.boxever.api.serializer.GuestSerde;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Properties;

public class BoxeverLocateGuest {

    private static final String API_KEY = "iluatejwwu4e1qw9cfd6ulzqiqcei6yy";
    private static final String API_SECRET = "fZG2TuIVxsJmFWOx3IaXIoxx70GPL1iu";
    private static final String BASE_URL = "https://api.boxever.com/v2";

    private static final String  GUEST = "/guests";


    public static void main(String[] args) throws IOException {
        Properties config = setUpStreamProperties();

        Producer<String, String> errorProducer = SetUpErrorTopic.invoke();

        KStreamBuilder builder = new KStreamBuilder();

        setJsonMapper();

        KStream<String, GuestWrapper> rawData = builder.stream("boxever-locate");
        rawData.mapValues( val ->  locateGuest(errorProducer, val));

        rawData.to( "boxever-consume");

        KafkaStreams streams = new KafkaStreams(builder, config);
        streams.start();

        // shutdown hook to correctly close the streams application
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            streams.close();
            errorProducer.close();
        }));

    }

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
    private static Properties setUpStreamProperties() {
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "boxever-stream-locate");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        //config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
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

    private static GuestWrapper locateGuest(Producer<String, String> producer, GuestWrapper guest) {
        try {
            System.out.println("locateGuest: " + guest);

            GuestWrapper retVal = null;

            if (guest != null & guest.getGuest() != null){
                final String email = guest.getGuest().getEmails().get(0);

                final String href = getGuestHref(email);
                guest.setHref(href);

                setGuestObj(guest, href);

                retVal =  guest;
            }

            return retVal;

        }catch (Exception e) {
            // log + ignore/skip the corrupted message
            System.err.println("Could not deserialize record: " + e.getMessage());
            producer.send(new ProducerRecord<String, String>("boxever-error", (guest == null ? "Null" : guest.toString()), e.getMessage()));

        }

        return null;
    }

    private static void setGuestObj(GuestWrapper guest, String href) throws UnirestException {
        final HttpResponse<Guest> getResponse = Unirest.get(href)
                .basicAuth(API_KEY, API_SECRET)
                .asObject(Guest.class);

        final Guest boxeverGuest = getResponse.getBody();
        final Guest ilGuest = guest.getGuest();

        boxeverGuest.setFirstName(ilGuest.getFirstName());
        boxeverGuest.setLastName(ilGuest.getLastName());
        boxeverGuest.setDateOfBirth(ilGuest.getDateOfBirth());
        boxeverGuest.setEmails(ilGuest.getEmails());
        boxeverGuest.setStreet(ilGuest.getStreet());
        guest.setGuest(boxeverGuest);
    }

    private static String getGuestHref(String email) throws UnirestException {
        HttpResponse<JsonNode> jsonResponse = Unirest.get(BASE_URL + GUEST + "?email=" + email).
                basicAuth(API_KEY, API_SECRET).
                asJson();
        System.out.println("jsonResponse: " + jsonResponse);

        final JSONObject refObj = (JSONObject)jsonResponse.getBody().getObject().getJSONArray("items").get(0);
        return refObj.getString("href");
    }


    private static class SetUpErrorTopic {
        private static Producer<String, String> invoke() {
            Properties producerConfig = new Properties();
            producerConfig.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:2181");
            producerConfig.put(ProducerConfig.ACKS_CONFIG, "all");
            producerConfig.put(ProducerConfig.RETRIES_CONFIG, 0);
            return new KafkaProducer<>(producerConfig, new StringSerializer(),  new StringSerializer());
        }
    }
}
