package com.fleetops.fleet.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fleetops.fleet.dto.FleetRequestDTO;
import com.fleetops.fleet.dto.FleetResponseDTO;

public interface FleetService {

    FleetResponseDTO createFleet(FleetRequestDTO request);
    Page<FleetResponseDTO> getAllFleets(Pageable pageable);
    FleetResponseDTO getFleetById(Long id);
    FleetResponseDTO updateFleet(Long id, FleetRequestDTO request);
    void deleteFleet(Long id);
    
}