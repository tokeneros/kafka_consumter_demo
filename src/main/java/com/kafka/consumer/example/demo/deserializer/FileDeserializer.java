package com.kafka.consumer.example.demo.deserializer;

import org.apache.kafka.common.serialization.Deserializer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: eros
 * @Description:
 * @Date: Created in 2020/4/8 10:19
 * @Version: 1.0
 * @Modified By:
 */
public class FileDeserializer implements Deserializer<Map> {
    @Override
    public Map deserialize(String topic, byte[] data) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(data);
             ObjectInput in = new ObjectInputStream(bis)){
            Object o = in.readObject();
            if (o instanceof Map) {
                return (Map) o;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new HashMap<String, String>();
    }

}
