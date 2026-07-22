package com.fleetops.shipment.dto;

import java.time.LocalDateTime;

import com.fleetops.shipment.entity.ShipmentStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShipmentResponseDTO {

    private Long id;

    private String shipmentNumber;

    private String source;

    private String destination;

    private String cargoDescription;

    private Double weight;

    private ShipmentStatus status;

    private LocalDateTime dispatchDate;

    private LocalDateTime deliveryDate;

    private LocalDateTime createdAt;

    private Long vehicleId;

    private String vehicleRegistrationNumber;

    private Long driverId;

    private String driverName;

}