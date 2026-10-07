package com.fleetops.notification.service.impl;

import com.fleetops.common.exception.ResourceNotFoundException;
import com.fleetops.notification.dto.NotificationResponseDTO;
import com.fleetops.notification.entity.Notification;
import com.fleetops.notification.mapper.NotificationMapper;
import com.fleetops.notification.repository.NotificationRepository;
import com.fleetops.notification.service.NotificationService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl
        implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public NotificationResponseDTO createNotification(
            Long shipmentId,
            String shipmentNumber,
            String type,
            String message) {

        Notification notification = Notification.builder()
                .shipmentId(shipmentId)
                .shipmentNumber(shipmentNumber)
                .type(type)
                .message(message)
                .build();

        Notification savedNotification =
                notificationRepository.save(notification);

        return notificationMapper.toResponse(savedNotification);
    }

    @Override
    public List<NotificationResponseDTO> getAllNotifications() {

        return notificationRepository.findAll()
                .stream()
                .map(notificationMapper::toResponse)
                .toList();
    }

    @Override
    public List<NotificationResponseDTO> getNotificationsByShipment(
            Long shipmentId) {

        return notificationRepository
                .findByShipmentId(shipmentId)
                .stream()
                .map(notificationMapper::toResponse)
                .toList();
    }

    @Override
    public List<NotificationResponseDTO> getUnreadNotifications() {

        return notificationRepository
                .findByReadFalse()
                .stream()
                .map(notificationMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public NotificationResponseDTO markAsRead(Long id) {

        Notification notification =
                notificationRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Notification not found with id " + id
                                ));

        notification.setRead(true);

        Notification updatedNotification =
                notificationRepository.save(notification);

        return notificationMapper.toResponse(updatedNotification);
    }
}