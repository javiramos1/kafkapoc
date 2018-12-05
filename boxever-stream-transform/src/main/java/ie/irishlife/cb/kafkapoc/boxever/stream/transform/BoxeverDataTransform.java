package ie.irishlife.cb.kafkapoc.boxever.stream.transform;


import ie.irishlife.cb.kafkapoc.boxever.api.cdc.CDCData;
import ie.irishlife.cb.kafkapoc.boxever.api.cdc.Payload;
import ie.irishlife.cb.kafkapoc.boxever.api.model.Guest;
import ie.irishlife.cb.kafkapoc.boxever.api.model.GuestWrapper;
import ie.irishlife.cb.kafkapoc.boxever.api.model.Identifier;
import ie.irishlife.cb.kafkapoc.boxever.api.serializer.CDCSerde;
import ie.irishlife.cb.kafkapoc.boxever.api.serializer.GuestSerde;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import static ie.irishlife.cb.kafkapoc.boxever.api.constants.KafkaConstants.*;


/**
 * Stream App that transform CDC data into Boxever Model
 * It gather the data from Irish Life and transform it into the Boxever Model.
 *
 */
public class BoxeverDataTransform {


    private final static Logger LOG = Logger.getLogger(BoxeverDataTransform.class);

    /**
     * Set up consumer properties. Uses env variables for docker.
     * @return Properties for Kafka
     */
    private static Properties setUpStreamProperties() {
        Properties config = new Properties();

        config.put(StreamsConfig.APPLICATION_ID_CONFIG, System.getenv(KAFKA_TOPIC) == null
                ? "boxever-cdc-stream" : System.getenv(KAFKA_TOPIC) );

        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, System.getenv(KAFKA_BROKER) == null
                ? DEFAULT_BROKER : System.getenv(KAFKA_BROKER));

        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, CDCSerde.class);
        config.put(
                StreamsConfig.PRODUCER_PREFIX + ProducerConfig.INTERCEPTOR_CLASSES_CONFIG,
                "io.confluent.monitoring.clients.interceptor.MonitoringProducerInterceptor");

        config.put(
                StreamsConfig.CONSUMER_PREFIX + ConsumerConfig.INTERCEPTOR_CLASSES_CONFIG,
                "io.confluent.monitoring.clients.interceptor.MonitoringConsumerInterceptor");
        return config;
    }

    /**
     * Error topic producer
     */
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
     * Main Method
     * @param args none used
     * @throws IOException
     */
    public static void main(String[] args) {

        LOG.info("App Starting...");

        Properties config = setUpStreamProperties();

        LOG.info(" Kafka Properties set: " + config);

        final Producer<String, String> errorProducer = SetUpErrorTopic.invoke();

        LOG.info(" errorProducer initialized: " + errorProducer);

        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, CDCData> rawData = builder.stream("cdc");

        //Transform into <ClientID,GuestWrapper> key value pair

        KStream<String, GuestWrapper>  guestData  = rawData.map( (key, val) -> {

            Map<String, Object> json =  (Map) val.getPayload().getBefore();

            LOG.debug("NEW MESSAGE: \n" + val.getPayload());

            final String clientId = (String) json.get("CLIENTID");
                   return new KeyValue<String, GuestWrapper>(clientId,
                            processCDC(errorProducer, val));
                });


        GuestSerde toSerde = new GuestSerde();
        guestData.to("boxever-locate", Produced.valueSerde(toSerde));

        LOG.info(" KStream initialized: " + guestData);

        final KafkaStreams streams = new KafkaStreams(builder.build(), config);
        streams.start();

        LOG.info(" KStream started: " + streams);

        // shutdown hook to correctly close the streams application
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOG.warn(" BoxeverDataTransform stopped ");
            streams.close();
            errorProducer.close();
            LOG.warn(" BoxeverDataTransform resources closed ");
        }));

    }


    /**
     * Process CDC recortds from Oracle connector
     * @param producer Error topic producer to track errors
     * @param val CDC entry
     * @return GuestWrapper containing Boxever Guest Object
     */
    private static GuestWrapper processCDC(Producer<String, String> producer, CDCData val) {
        try {
            LOG.info("processCDC: " + val);
            final Payload changedData = val.getPayload();
            final String operation = changedData.getOPERATION();

            final GuestWrapper retVal = new GuestWrapper();
            retVal.setOperation(operation);
            retVal.setGuest(mapGuest(changedData.getData(), changedData.getTABLENAME()));
            retVal.setTimeStamp(new Date());

            LOG.info("Mapped Object " + retVal);

            return retVal;
        }catch (Exception e) {
                // log + ignore/skip the corrupted message
                LOG.error("Could not deserialize record: " + e.getMessage());
                producer.send(new ProducerRecord<String, String>("boxever-error", val.toString(), e.getMessage()));
        }

        return null;
    }

    private static Guest mapGuest(Object data, String table) {

        //TODO: Map according to table name, add more values. Check for null
        Guest retVal = new Guest();
        Map<String, Object> json =  (Map) data;
        retVal.setFirstName( (String) json.get("FORENAME"));
        retVal.setLastName((String) json.get("SURNAME"));
        retVal.setEmails(Arrays.asList(new String[]{(String) json.get("EMAIL")}));
        retVal.setStreet(Arrays.asList(new String[]{(String) json.get("ADDRESS")}));

        Identifier id = new Identifier();
        id.setId((String) json.get("CLIENTID"));
        retVal.setIdentifiers(Arrays.asList(new Identifier[]{id}));
        retVal.setDateOfBirth(json.get("DATEOFBIRTH") == null ? null :  new Date((Long)json.get("DATEOFBIRTH")));

        return retVal;
    }


}



