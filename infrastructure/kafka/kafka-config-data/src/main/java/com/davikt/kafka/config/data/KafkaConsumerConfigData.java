package com.davikt.kafka.config.data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kafka-consumer-config")
public record KafkaConsumerConfigData(
        String keyDeserializer,
        String valueDeserializer,
        String autoOffsetReset,
        String specificAvroReaderKey,
        String specificAvroReader,
        Boolean batchListener,
        Boolean autoStartup,
        Integer concurrencyLevel,
        Integer sessionTimeoutMs,
        Integer heartbeatIntervalMs,
        Integer maxPollIntervalMs,
        Long pollTimeoutMs,
        Integer maxPollRecords,
        Integer maxPartitionFetchBytesDefault,
        Integer maxPartitionFetchBytesBoostFactor
) {
}
