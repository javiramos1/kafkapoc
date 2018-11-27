package ie.irishlife.cb.kafkapoc.boxever.api.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import ie.irishlife.cb.kafkapoc.boxever.api.cdc.CDCData;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class CDCSerializer implements Serializer<CDCData> {

    @Override
    public void configure(Map map, boolean b) {

    }

    @Override
    public byte[] serialize(String s, CDCData o) {
        byte[] retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            retVal = objectMapper.writeValueAsString(o).getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retVal;
    }

    @Override
    public void close() {
    }
}