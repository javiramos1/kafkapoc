package ie.irishlife.cb.kafkapoc.boxever.stream.transform;


import ie.irishlife.cb.kafkapoc.boxever.api.cdc.CDCData;
import ie.irishlife.cb.kafkapoc.boxever.api.cdc.Data;
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

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;


/**
 * Stream App that transform CDC data into Boxever Model
 * It gather the data from Irish Life and transform it into the Boxever Model.
 *
 */
public class BoxeverDataTransform {
    public static void main(String[] args) throws IOException {
        Properties config = setUpStreamProperties();

        Producer<String, String> errorProducer = SetUpErrorTopic.invoke();

        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, CDCData> rawData = builder.stream("cdc");

        //Transform into <ClientID,GuestWrapper> key value pair
        KStream<String, GuestWrapper>  guestData  = rawData.map( (key, val) ->
                new KeyValue<String, GuestWrapper>(val.getPayload().getBefore().getCLIENTID(),  processCDC(errorProducer, val)) );


        GuestSerde toSerde = new GuestSerde();
        guestData.to("boxever-locate", Produced.valueSerde(toSerde));

        KafkaStreams streams = new KafkaStreams(builder.build(), config);
        streams.start();

        // shutdown hook to correctly close the streams application
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            streams.close();
            errorProducer.close();
        }));

    }

    /**
     * Set up consumer properties. TODO: extract properties
     * @return Properties for Kafka
     */
    private static Properties setUpStreamProperties() {
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "boxever-cdc-stream");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        // config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
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
     * Process CDC recortds from Oracle connector
     * @param producer Error topic producer to track errors
     * @param val CDC entry
     * @return GuestWrapper containing Boxever Guest Object
     */
    private static GuestWrapper processCDC(Producer<String, String> producer, CDCData val) {
        try {
            System.out.println("processCDC: " + val);
            final Payload changedData = val.getPayload();
            final String operation = changedData.getOPERATION();

            final GuestWrapper retVal = new GuestWrapper();
            retVal.setOperation(operation);
            retVal.setGuest(mapGuest(changedData.getData()));
            retVal.setTimeStamp(new Date());

            System.out.println("Mapped Object " + retVal);

            return retVal;
        }catch (Exception e) {
                // log + ignore/skip the corrupted message
                System.err.println("Could not deserialize record: " + e.getMessage());
                producer.send(new ProducerRecord<String, String>("boxever-error", val.toString(), e.getMessage()));
        }

        return null;
    }

    private static Guest mapGuest(Data data) {
        Guest retVal = new Guest();
        retVal.setFirstName(data.getFORENAME());
        retVal.setLastName(data.getSURNAME());
        retVal.setEmails(Arrays.asList(new String[]{data.getEMAIL()}));
        retVal.setStreet(Arrays.asList(new String[]{data.getADDRESS()}));
        retVal.setCountry("Ireland");
        Identifier id = new Identifier();
        id.setId(data.getCLIENTID());
        retVal.setIdentifiers(Arrays.asList(new Identifier[]{id}));
        retVal.setDateOfBirth(data.getDATEOFBIRTH());

        return retVal;
    }

    /**
     * Error topic producer
     */
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



