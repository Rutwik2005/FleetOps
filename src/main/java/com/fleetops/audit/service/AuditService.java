
package com.fleetops.audit.service;

import com.fleetops.audit.dto.AuditLogResponseDTO;

import java.util.List;

public interface AuditService {

    AuditLogResponseDTO recordEvent(
            String eventType,
            Long shipmentId,
            String shipmentNumber,
            Long vehicleId,
            Long driverId,
            java.time.LocalDateTime eventTimestamp
    );

    List<AuditLogResponseDTO> getAllAuditLogs();

    List<AuditLogResponseDTO> getShipmentHistory(Long shipmentId);

    List<AuditLogResponseDTO> getByEventType(String eventType);
}
