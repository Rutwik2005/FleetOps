package com.fleetops.dispatch.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fleetops.shipment.entity.Shipment;

@Entity
@Table(name = "dispatches")
@Getter
@Setter
@NoArgsConstructor
public class Dispatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "shipment_id",
        nullable = false,
        unique = true
    )
    private Shipment shipment;

    @Column(nullable = false)
    private String dispatcherName;

    private LocalDateTime dispatchTime;

    private LocalDateTime expectedDelivery;

    private LocalDateTime actualDelivery;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DispatchStatus status;

    private String remarks;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();

        if (status == null) {
            status = DispatchStatus.PENDING;
        }
    }
}