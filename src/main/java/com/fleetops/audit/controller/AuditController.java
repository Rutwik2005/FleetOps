
package com.fleetops.audit.controller;

import com.fleetops.audit.dto.AuditLogResponseDTO;
import com.fleetops.audit.service.AuditService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
@RequiredArgsConstructor
public class AuditController {

    private final AuditService auditService;

    @GetMapping
    public List<AuditLogResponseDTO> getAllAuditLogs() {
        return auditService.getAllAuditLogs();
    }

    @GetMapping("/shipment/{shipmentId}")
    public List<AuditLogResponseDTO> getShipmentHistory(
            @PathVariable Long shipmentId) {
        return auditService.getShipmentHistory(shipmentId);
    }

    @GetMapping("/event/{eventType}")
    public List<AuditLogResponseDTO> getByEventType(
            @PathVariable String eventType) {
        return auditService.getByEventType(eventType);
    }
}
