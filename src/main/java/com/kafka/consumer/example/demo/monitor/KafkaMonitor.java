package com.kafka.consumer.example.demo.monitor;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Author: eros
 * @Description:
 * @Date: Created in 2019/12/30 10:34
 * @Version: 1.0
 * @Modified By:
 */
@Component
public class KafkaMonitor {

    @KafkaListener(containerFactory = "kafkaBatchListener3", topics = {"test1", "test2", "test3"})
    public void monitor(List<ConsumerRecord<?, ?>> records, Acknowledgment ack){
        try {
            System.out.println(records.size());
            records.forEach(record -> {
                System.out.println("ThreadName : " + Thread.currentThread().getName() + "topic : " + record.topic() + ", partition : " + record.partition() + ", offset : " + record.offset() + ", value : " + record.value().toString());
            });
        } catch (Exception e) {
            System.out.println("Kafka监听异常");
        } finally {
            ack.acknowledge();
        }
    }

}
