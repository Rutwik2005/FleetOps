
package com.fleetops.audit.repository;

import com.fleetops.audit.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditLogRepository
        extends JpaRepository<AuditLog, Long> {

    List<AuditLog> findByShipmentIdOrderByEventTimestampAsc(
            Long shipmentId);

    List<AuditLog> findByEventTypeOrderByEventTimestampDesc(
            String eventType);
}
