package com.fleetops.event.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fleetops.event.config.KafkaTopicConfig;
import com.fleetops.event.dto.ShipmentEvent;

@Service
public class ShipmentEventConsumer {

    @KafkaListener(
            topics = KafkaTopicConfig.SHIPMENT_EVENTS,
            groupId = "fleetops-group"
    )
    public void consumeShipmentEvent(ShipmentEvent event) {

        System.out.println(
                "Received shipment event: "
                + event.getEventType()
                + " | Shipment ID: "
                + event.getShipmentId()
        );
    }
}