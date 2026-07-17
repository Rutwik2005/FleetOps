package com.fleetops.fleet.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.http.HttpStatus;
import com.fleetops.fleet.dto.FleetRequestDTO;
import com.fleetops.fleet.dto.FleetResponseDTO;
import com.fleetops.fleet.service.FleetService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/fleets")
public class FleetController {
	private final FleetService fleetService;

	public FleetController(FleetService fleetService) {
	
		this.fleetService = fleetService;
	}
	@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FleetResponseDTO createFleet(
            @Valid @RequestBody FleetRequestDTO request) {

        return fleetService.createFleet(request);
    }
	
	@GetMapping
	public List<FleetResponseDTO> getAllFleets() {
	    return fleetService.getAllFleets();
	}
	@GetMapping("/{id}")
	public FleetResponseDTO getFleetById(@PathVariable Long id) {
		return fleetService.getFleetById(id);
	}
	@PutMapping("/{id}")
	public FleetResponseDTO updateFleet(@PathVariable Long id, @Valid @RequestBody FleetRequestDTO request) {
		return fleetService.updateFleet(id, request);
	}
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteFleet(@PathVariable Long id) {
		fleetService.deleteFleet(id);
	}
}
