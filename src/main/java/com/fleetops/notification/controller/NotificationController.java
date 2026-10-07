package com.fleetops.notification.controller;

import com.fleetops.notification.dto.NotificationResponseDTO;
import com.fleetops.notification.service.NotificationService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public List<NotificationResponseDTO> getAllNotifications() {

        return notificationService.getAllNotifications();
    }

    @GetMapping("/shipment/{shipmentId}")
    public List<NotificationResponseDTO> getByShipment(
            @PathVariable Long shipmentId) {

        return notificationService
                .getNotificationsByShipment(shipmentId);
    }

    @GetMapping("/unread")
    public List<NotificationResponseDTO> getUnreadNotifications() {

        return notificationService.getUnreadNotifications();
    }

    @PatchMapping("/{id}/read")
    public NotificationResponseDTO markAsRead(
            @PathVariable Long id) {

        return notificationService.markAsRead(id);
    }
}