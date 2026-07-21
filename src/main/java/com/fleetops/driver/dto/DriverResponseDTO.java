package com.fleetops.driver.dto;

import java.time.LocalDateTime;

import com.fleetops.driver.entity.DriverStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverResponseDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String licenseNumber;

    private String phoneNumber;

    private DriverStatus status;

    private Long fleetId;

    private String fleetName;

    private LocalDateTime createdAt;

}