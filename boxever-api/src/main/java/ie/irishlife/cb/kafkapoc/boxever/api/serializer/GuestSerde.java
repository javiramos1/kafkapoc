package ie.irishlife.cb.kafkapoc.boxever.api.serializer;

import ie.irishlife.cb.kafkapoc.boxever.api.model.boxever.GuestWrapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class GuestSerde implements Serde<GuestWrapper> {
    private GuestSerializer serializer = new GuestSerializer();
    private GuestDeSerializer deserializer = new GuestDeSerializer();

    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public void close() {

    }

    @Override
    public Serializer<GuestWrapper> serializer() {
        return serializer;
    }

    @Override
    public Deserializer<GuestWrapper> deserializer() {
        return deserializer;
    }
}
