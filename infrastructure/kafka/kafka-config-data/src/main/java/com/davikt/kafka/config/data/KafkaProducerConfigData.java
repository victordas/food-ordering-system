package com.davikt.kafka.config.data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kafka-producer-config")
public record KafkaProducerConfigData(
        String keySerializerClass,
        String valueSerializerClass,
        String compressionType,
        String acks,
        Integer batchSize,
        Integer batchSizeBoostFactor,
        Integer lingerMs,
        Integer requestTimeoutMs,
        Integer retryCount
) {
}
