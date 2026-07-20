package com.fleetops.vehicle.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fleetops.vehicle.dto.VehicleRequestDTO;
import com.fleetops.vehicle.dto.VehicleResponseDTO;

public interface VehicleService {
	VehicleResponseDTO createVehicle(VehicleRequestDTO request);

    Page<VehicleResponseDTO> getAllVehicles(Pageable pageable);

    VehicleResponseDTO getVehicleById(Long id);

    VehicleResponseDTO updateVehicle(Long id, VehicleRequestDTO request);

    void deleteVehicle(Long id);

}
