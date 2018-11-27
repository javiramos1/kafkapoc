package ie.irishlife.cb.kafkapoc.boxever.api.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import ie.irishlife.cb.kafkapoc.boxever.api.model.GuestWrapper;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class GuestSerializer implements Serializer<GuestWrapper> {

    @Override
    public void configure(Map map, boolean b) {

    }

    @Override
    public byte[] serialize(String s, GuestWrapper o) {
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