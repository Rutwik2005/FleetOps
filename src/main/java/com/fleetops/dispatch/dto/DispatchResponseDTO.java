package com.fleetops.dispatch.dto;

import java.time.LocalDateTime;

import com.fleetops.dispatch.entity.DispatchStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DispatchResponseDTO {

    private Long id;

    private Long shipmentId;

    private String shipmentNumber;

    private String dispatcherName;

    private LocalDateTime dispatchTime;

    private LocalDateTime expectedDelivery;

    private LocalDateTime actualDelivery;

    private DispatchStatus status;

    private String remarks;

    private LocalDateTime createdAt;
}