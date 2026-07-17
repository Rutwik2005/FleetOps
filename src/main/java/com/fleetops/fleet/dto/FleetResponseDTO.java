package com.fleetops.fleet.dto;

import java.time.LocalDateTime;

import com.fleetops.fleet.entity.FleetStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FleetResponseDTO {

    private Long id;

    private String fleetName;

    private String companyName;

    private String headOffice;

    private String contactEmail;

    private String contactPhone;

    private FleetStatus status;

    private LocalDateTime createdAt;
}