
package com.fleetops.audit.mapper;

import com.fleetops.audit.dto.AuditLogResponseDTO;
import com.fleetops.audit.entity.AuditLog;
import org.springframework.stereotype.Component;

@Component
public class AuditLogMapper {

    public AuditLogResponseDTO toResponse(AuditLog auditLog) {
        return AuditLogResponseDTO.builder()
                .id(auditLog.getId())
                .eventType(auditLog.getEventType())
                .shipmentId(auditLog.getShipmentId())
                .shipmentNumber(auditLog.getShipmentNumber())
                .vehicleId(auditLog.getVehicleId())
                .driverId(auditLog.getDriverId())
                .description(auditLog.getDescription())
                .eventTimestamp(auditLog.getEventTimestamp())
                .recordedAt(auditLog.getRecordedAt())
                .build();
    }
}
