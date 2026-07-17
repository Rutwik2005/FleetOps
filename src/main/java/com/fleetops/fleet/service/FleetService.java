package com.fleetops.fleet.service;

import java.util.List;

import com.fleetops.fleet.dto.FleetRequestDTO;
import com.fleetops.fleet.dto.FleetResponseDTO;

public interface FleetService {

    FleetResponseDTO createFleet(FleetRequestDTO request);
    List<FleetResponseDTO> getAllFleets();
    FleetResponseDTO getFleetById(Long id);
    FleetResponseDTO updateFleet(Long id, FleetRequestDTO request);
    void deleteFleet(Long id);
    
}