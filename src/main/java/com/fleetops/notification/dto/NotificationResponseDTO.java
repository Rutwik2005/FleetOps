package com.fleetops.notification.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponseDTO {

    private Long id;

    private Long shipmentId;

    private String shipmentNumber;

    private String type;

    private String message;

    private boolean read;

    private LocalDateTime createdAt;
}