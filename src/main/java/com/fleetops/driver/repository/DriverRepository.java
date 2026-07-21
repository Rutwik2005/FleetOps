package com.fleetops.driver.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fleetops.driver.entity.Driver;

public interface DriverRepository extends JpaRepository<Driver, Long> {
	Optional<Driver> findByLicenseNumber(String licenseNumber);

    boolean existsByLicenseNumber(String licenseNumber);

    Optional<Driver> findByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumber(String phoneNumber);

}
