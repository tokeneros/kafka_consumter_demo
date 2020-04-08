package com.kafka.consumer.example.demo.config;

import com.kafka.consumer.example.demo.config.properties.KafkaListenerProperties;
import com.kafka.consumer.example.demo.config.properties.KafkaProperties;
import com.kafka.consumer.example.demo.deserializer.FileDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: eros
 * @Description:
 * @Date: Created in 2020/1/2 15:00
 * @Version: 1.0
 * @Modified By:
 */
@Configuration
@EnableKafka
public class KafkaCustomerConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Autowired
    private KafkaListenerProperties kafkaListenerProperties;

    /************   kafka string customer  *************/
    public Map<String, Object> customerConfig(){
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getGroupId());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaProperties.getAutoOffsetReset());
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, kafkaProperties.getMaxPollRecords());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, kafkaProperties.getEnableAutoCommit());
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, kafkaProperties.getAutoCommitInterval());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getKeyDeserializer());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getValueDeserializer());
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, kafkaProperties.getSessionTimeoutMs());
        return props;
    }

    public ConsumerFactory<String, String> consumerFactory(){
        return new DefaultKafkaConsumerFactory<>(customerConfig());
    }

    /**
     * @Description:
     * @Author: eros
     * @Date: 2020/1/3 10:34
     * @param
     * @Return: org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory<java.lang.String,java.lang.String>
     * @Exception:
     */
    private ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        //批量消费
        factory.setBatchListener(Boolean.TRUE);
        //如果消息队列中没有消息，等待timeout毫秒后，调用poll()方法。
        // 如果队列中有消息，立即消费消息，每次消费的消息的多少可以通过max.poll.records配置。
        //手动提交无需配置
        factory.getContainerProperties().setPollTimeout(Long.valueOf(kafkaListenerProperties.getPollTimeout()));
        //设置提交偏移量的方式， MANUAL_IMMEDIATE 表示消费一条提交一次；MANUAL表示批量提交一次
//        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        return factory;
    }

    /**
     * 并发数3
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "kafkaBatchListener3")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaBatchListener3() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = kafkaListenerContainerFactory();
        factory.setConcurrency(Integer.valueOf(kafkaListenerProperties.getConcurrency()));
        return factory;
    }

    /************   kafka map customer  *************/
    public Map<String, Object> customerMapConfig(){
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getGroupId());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaProperties.getAutoOffsetReset());
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, kafkaProperties.getMaxPollRecords());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, kafkaProperties.getEnableAutoCommit());
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, kafkaProperties.getAutoCommitInterval());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaProperties.getKeyDeserializer());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, FileDeserializer.class);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, kafkaProperties.getSessionTimeoutMs());
        return props;
    }

    public ConsumerFactory<String, Map<String, Object>> consumerMapFactory(){
        return new DefaultKafkaConsumerFactory<>(customerMapConfig());
    }

    /**
     * @Description:
     * @Author: eros
     * @Date: 2020/1/3 10:34
     * @param
     * @Return: org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory<java.lang.String,java.lang.String>
     * @Exception:
     */
    private ConcurrentKafkaListenerContainerFactory<String, Map<String, Object>> kafkaListenerMapContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Map<String, Object>> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerMapFactory());
        //批量消费
        factory.setBatchListener(Boolean.TRUE);
        //如果消息队列中没有消息，等待timeout毫秒后，调用poll()方法。
        // 如果队列中有消息，立即消费消息，每次消费的消息的多少可以通过max.poll.records配置。
        //手动提交无需配置
        factory.getContainerProperties().setPollTimeout(Long.valueOf(kafkaListenerProperties.getPollTimeout()));
        //设置提交偏移量的方式， MANUAL_IMMEDIATE 表示消费一条提交一次；MANUAL表示批量提交一次
//        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        return factory;
    }

    /**
     * 并发数3
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "kafkaBatchListenerMap3")
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Map<String, Object>>> kafkaBatchListenerMap3() {
        ConcurrentKafkaListenerContainerFactory<String, Map<String, Object>> factory = kafkaListenerMapContainerFactory();
        factory.setConcurrency(Integer.valueOf(kafkaListenerProperties.getConcurrency()));
        return factory;
    }

}
