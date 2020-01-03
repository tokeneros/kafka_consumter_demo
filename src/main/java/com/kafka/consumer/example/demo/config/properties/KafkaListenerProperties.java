package com.kafka.consumer.example.demo.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: eros
 * @Description:
 * @Date: Created in 2020/1/2 18:07
 * @Version: 1.0
 * @Modified By:
 */
@Component
@ConfigurationProperties(prefix = KafkaListenerProperties.PREFIX)
@Data
public class KafkaListenerProperties {

    public static final String PREFIX = "spring.kafka.listener";

    private String concurrency;

    private String pollTimeout;

}
