package ie.irishlife.cb.kafkapoc.boxever.api.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import ie.irishlife.cb.kafkapoc.boxever.api.model.boxever.GuestWrapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.Serializable;
import java.util.Map;


public class GuestDeSerializer<T extends Serializable> implements Deserializer<GuestWrapper> {


    @Override
    public void configure(Map map, boolean b) {

    }

    @Override
    public GuestWrapper deserialize(String s, byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper();

        GuestWrapper data = null;
        try {
            data = mapper.readValue(bytes, GuestWrapper.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;

    }

    @Override
    public void close() {

    }
}