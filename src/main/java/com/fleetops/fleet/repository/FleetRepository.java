package com.fleetops.fleet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fleetops.fleet.entity.Fleet;

public interface FleetRepository extends JpaRepository<Fleet, Long> {
   Optional<Fleet> findByContactEmail(String contactEmail);
   boolean existsByContactEmail(String contactEmail);
}
