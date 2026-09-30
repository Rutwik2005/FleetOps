package com.fleetops.dispatch.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fleetops.dispatch.entity.Dispatch;

@Repository
public interface DispatchRepository extends JpaRepository<Dispatch, Long> {

    Optional<Dispatch> findByShipmentId(Long shipmentId);

    boolean existsByShipmentId(Long shipmentId);
}