package com.kafka.consumer.example.demo.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: eros
 * @Description:
 * @Date: Created in 2020/1/2 15:01
 * @Version: 1.0
 * @Modified By:
 */
@Component
@ConfigurationProperties(prefix = KafkaProperties.PREFIX)
@Data
public class KafkaProperties {

    public static final String PREFIX = "spring.kafka.consumer";

    private String bootstrapServers;

    private String groupId;

    private String maxPollRecords;

    private String autoOffsetReset;

    private String enableAutoCommit;

    private String autoCommitInterval;

    private String keyDeserializer;

    private String valueDeserializer;

    private String sessionTimeoutMs = "15000";

}
