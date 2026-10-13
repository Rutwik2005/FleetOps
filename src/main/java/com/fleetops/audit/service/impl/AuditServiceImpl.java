
package com.fleetops.audit.service.impl;

import com.fleetops.audit.dto.AuditLogResponseDTO;
import com.fleetops.audit.entity.AuditLog;
import com.fleetops.audit.mapper.AuditLogMapper;
import com.fleetops.audit.repository.AuditLogRepository;
import com.fleetops.audit.service.AuditService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditLogRepository auditLogRepository;
    private final AuditLogMapper auditLogMapper;

    @Override
    @Transactional
    public AuditLogResponseDTO recordEvent(
            String eventType,
            Long shipmentId,
            String shipmentNumber,
            Long vehicleId,
            Long driverId,
            LocalDateTime eventTimestamp) {

        AuditLog auditLog = AuditLog.builder()
                .eventType(eventType)
                .shipmentId(shipmentId)
                .shipmentNumber(shipmentNumber)
                .vehicleId(vehicleId)
                .driverId(driverId)
                .description(buildDescription(eventType, shipmentNumber))
                .eventTimestamp(
                        eventTimestamp != null
                                ? eventTimestamp
                                : LocalDateTime.now())
                .build();

        AuditLog savedLog = auditLogRepository.save(auditLog);

        return auditLogMapper.toResponse(savedLog);
    }

    private String buildDescription(
            String eventType, String shipmentNumber) {

        return switch (eventType) {
            case "SHIPMENT_ASSIGNED" ->
                    "Shipment " + shipmentNumber + " was assigned.";
            case "SHIPMENT_DISPATCHED" ->
                    "Shipment " + shipmentNumber + " was dispatched.";
            case "SHIPMENT_COMPLETED" ->
                    "Shipment " + shipmentNumber + " was completed.";
            case "SHIPMENT_CANCELLED" ->
                    "Shipment " + shipmentNumber + " was cancelled.";
            default ->
                    "Event " + eventType + " recorded for shipment "
                            + shipmentNumber + ".";
        };
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuditLogResponseDTO> getAllAuditLogs() {
        return auditLogRepository.findAll()
                .stream()
                .map(auditLogMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuditLogResponseDTO> getShipmentHistory(Long shipmentId) {
        return auditLogRepository
                .findByShipmentIdOrderByEventTimestampAsc(shipmentId)
                .stream()
                .map(auditLogMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuditLogResponseDTO> getByEventType(String eventType) {
        return auditLogRepository
                .findByEventTypeOrderByEventTimestampDesc(eventType)
                .stream()
                .map(auditLogMapper::toResponse)
                .toList();
    }
}
