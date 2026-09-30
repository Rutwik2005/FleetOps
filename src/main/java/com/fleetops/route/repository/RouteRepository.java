package com.fleetops.route.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fleetops.route.entity.Route;

public interface RouteRepository extends JpaRepository<Route, Long> {
   Optional<Route> findByShipmentId(Long shipmentId);
   
   boolean existsByShipmentId(Long shipmentId);
}
