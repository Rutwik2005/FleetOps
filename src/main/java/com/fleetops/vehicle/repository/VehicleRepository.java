package com.fleetops.vehicle.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fleetops.vehicle.entity.Vehicle;
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
   Optional<Vehicle> findByRegistrationNumber(String registrationNumber);
   
   boolean existsByRegistrationNumber(String registrationNumber);
   
}