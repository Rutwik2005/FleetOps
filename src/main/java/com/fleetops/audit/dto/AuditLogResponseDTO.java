
package com.fleetops.audit.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLogResponseDTO {

    private Long id;
    private String eventType;
    private Long shipmentId;
    private String shipmentNumber;
    private Long vehicleId;
    private Long driverId;
    private String description;
    private LocalDateTime eventTimestamp;
    private LocalDateTime recordedAt;
}
