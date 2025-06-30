package com.davikt.kafka.config.data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kafka-config")
public record KafkaConfigData(
        String bootstrapServers,
        String schemaRegistryUrlKey,
        String schemaRegistryUrl,
        Integer numOfPartitions,
        Short replicationFactor
) {
}
