package com.fleetops.vehicle.dto;

import java.time.LocalDateTime;

import com.fleetops.vehicle.entity.VehicleStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleResponseDTO {

    private Long id;

    private String registrationNumber;

    private String manufacturer;

    private String model;

    private Integer capacity;

    private VehicleStatus status;

    private Long fleetId;

    private String fleetName;

    private LocalDateTime createdAt;
}