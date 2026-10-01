package com.fleetops.event.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fleetops.event.config.KafkaTopicConfig;
import com.fleetops.event.dto.ShipmentEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShipmentEventProducer {

    private final KafkaTemplate<String, ShipmentEvent> kafkaTemplate;

    public void publishShipmentEvent(ShipmentEvent event) {

        kafkaTemplate.send(
                KafkaTopicConfig.SHIPMENT_EVENTS,
                String.valueOf(event.getShipmentId()),
                event
        );
    }
}