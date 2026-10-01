package com.fleetops.event.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentEvent {

    private ShipmentEventType eventType;

    private Long shipmentId;

    private String shipmentNumber;

    private Long vehicleId;

    private Long driverId;

    private LocalDateTime timestamp;
}