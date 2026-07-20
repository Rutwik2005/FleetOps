package com.fleetops.vehicle.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleRequestDTO {

    @NotBlank(message = "Registration number is required")
    private String registrationNumber;

    @NotBlank(message = "Manufacturer is required")
    private String manufacturer;

    @NotBlank(message = "Model is required")
    private String model;

    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be greater than 0")
    private Integer capacity;

    @NotNull(message = "Fleet ID is required")
    private Long fleetId;
}