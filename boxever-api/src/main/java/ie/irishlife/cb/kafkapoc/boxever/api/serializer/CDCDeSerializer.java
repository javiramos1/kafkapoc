package ie.irishlife.cb.kafkapoc.boxever.api.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import ie.irishlife.cb.kafkapoc.boxever.api.cdc.CDCData;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.Serializable;
import java.util.Map;


public class CDCDeSerializer<T extends Serializable> implements Deserializer<CDCData> {


    @Override
    public void configure(Map map, boolean b) {

    }

    @Override
    public CDCData deserialize(String s, byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper();

        CDCData data = null;
        try {
            data = mapper.readValue(bytes, CDCData.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;

    }

    @Override
    public void close() {

    }
}