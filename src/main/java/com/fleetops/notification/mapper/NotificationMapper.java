package com.fleetops.notification.mapper;

import com.fleetops.notification.dto.NotificationResponseDTO;
import com.fleetops.notification.entity.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public NotificationResponseDTO toResponse(Notification notification) {

        return NotificationResponseDTO.builder()
                .id(notification.getId())
                .shipmentId(notification.getShipmentId())
                .shipmentNumber(notification.getShipmentNumber())
                .type(notification.getType())
                .message(notification.getMessage())
                .read(notification.isRead())
                .createdAt(notification.getCreatedAt())
                .build();
    }
}