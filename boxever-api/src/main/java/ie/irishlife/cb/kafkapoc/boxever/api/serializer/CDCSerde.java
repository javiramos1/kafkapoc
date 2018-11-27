package ie.irishlife.cb.kafkapoc.boxever.api.serializer;

import ie.irishlife.cb.kafkapoc.boxever.api.cdc.CDCData;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class CDCSerde implements Serde<CDCData> {
    private ie.irishlife.cb.kafkapoc.boxever.api.serializer.CDCSerializer serializer = new ie.irishlife.cb.kafkapoc.boxever.api.serializer.CDCSerializer();
    private CDCDeSerializer deserializer = new CDCDeSerializer();

    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public void close() {

    }

    @Override
    public Serializer<CDCData> serializer() {
        return serializer;
    }

    @Override
    public Deserializer<CDCData> deserializer() {
        return deserializer;
    }
}
