package com.fleetops.dispatch.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DispatchRequestDTO {

    @NotNull(message = "Shipment ID is required")
    private Long shipmentId;

    @NotBlank(message = "Dispatcher name is required")
    private String dispatcherName;

    @Future(message = "Expected delivery must be in the future")
    private LocalDateTime expectedDelivery;

    private String remarks;
}