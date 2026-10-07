package com.fleetops.notification.service;

import com.fleetops.notification.dto.NotificationResponseDTO;

import java.util.List;

public interface NotificationService {

    NotificationResponseDTO createNotification(
            Long shipmentId,
            String shipmentNumber,
            String type,
            String message
    );

    List<NotificationResponseDTO> getAllNotifications();

    List<NotificationResponseDTO> getNotificationsByShipment(
            Long shipmentId
    );

    List<NotificationResponseDTO> getUnreadNotifications();

    NotificationResponseDTO markAsRead(Long id);
}