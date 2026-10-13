
package com.fleetops.audit.consumer;

import com.fleetops.audit.service.AuditService;
import com.fleetops.event.dto.ShipmentEvent;

import lombok.RequiredArgsConstructor;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuditEventConsumer {

    private final AuditService auditService;

    @KafkaListener(
            topics = "shipment-events",
            groupId = "audit-group"
    )
    public void consumeShipmentEvent(ShipmentEvent event) {

        auditService.recordEvent(
                event.getEventType().name(),
                event.getShipmentId(),
                event.getShipmentNumber(),
                event.getVehicleId(),
                event.getDriverId(),
                event.getTimestamp()
        );
    }
}
