package com.fleetops.event.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    public static final String SHIPMENT_EVENTS =
            "shipment-events";

    @Bean
    public NewTopic shipmentEventsTopic() {

        return new NewTopic(
                SHIPMENT_EVENTS,
                1,
                (short) 1
        );
    }
}