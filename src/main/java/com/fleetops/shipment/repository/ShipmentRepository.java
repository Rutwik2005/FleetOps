package com.fleetops.shipment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fleetops.shipment.entity.Shipment;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    Optional<Shipment> findByShipmentNumber(String shipmentNumber);

    boolean existsByShipmentNumber(String shipmentNumber);
    
}
