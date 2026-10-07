package com.fleetops.notification.consumer;

import com.fleetops.event.dto.ShipmentEvent;
import com.fleetops.event.dto.ShipmentEventType;
import com.fleetops.notification.service.NotificationService;

import lombok.RequiredArgsConstructor;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationEventConsumer {

    private final NotificationService notificationService;

    @KafkaListener(
            topics = "shipment-events",
            groupId = "notification-group"
    )
    public void consumeShipmentEvent(ShipmentEvent event) {

        String message = buildMessage(event);

        notificationService.createNotification(
                event.getShipmentId(),
                event.getShipmentNumber(),
                event.getEventType().name(),
                message
        );
    }

    private String buildMessage(ShipmentEvent event) {

        return switch (event.getEventType()) {

            case SHIPMENT_ASSIGNED ->
                    "Shipment " + event.getShipmentNumber()
                            + " has been assigned.";

            case SHIPMENT_DISPATCHED ->
                    "Shipment " + event.getShipmentNumber()
                            + " has been dispatched.";

            case SHIPMENT_COMPLETED ->
                    "Shipment " + event.getShipmentNumber()
                            + " has been delivered.";

            case SHIPMENT_CANCELLED ->
                    "Shipment " + event.getShipmentNumber()
                            + " has been cancelled.";
        };
    }
}