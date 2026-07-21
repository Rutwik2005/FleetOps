package com.fleetops.driver.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fleetops.driver.dto.DriverRequestDTO;
import com.fleetops.driver.dto.DriverResponseDTO;

public interface DriverService {
	DriverResponseDTO createDriver(DriverRequestDTO request);

    Page<DriverResponseDTO> getAllDrivers(Pageable pageable);

    DriverResponseDTO getDriverById(Long id);

    DriverResponseDTO updateDriver(Long id, DriverRequestDTO request);

    void deleteDriver(Long id);
}
