package com.fleetops.route.dto;

import java.time.LocalDateTime;

import com.fleetops.route.entity.RouteStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RouteResponseDTO {

    private Long id;

    private Long shipmentId;

    private String shipmentNumber;

    private String source;

    private String destination;

    private Double distanceKm;

    private Integer estimatedDurationMinutes;

    private RouteStatus status;

    private LocalDateTime startedAt;

    private LocalDateTime completedAt;

    private LocalDateTime createdAt;
}