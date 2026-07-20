package com.fleetops.vehicle.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import com.fleetops.vehicle.dto.VehicleRequestDTO;
import com.fleetops.vehicle.dto.VehicleResponseDTO;
import com.fleetops.vehicle.service.VehicleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
@Validated
public class VehicleController {
   private final VehicleService vehicleService;
   
   @PostMapping
   @ResponseStatus(HttpStatus.CREATED)
   public VehicleResponseDTO createVehicle(@Valid @RequestBody VehicleRequestDTO request) {
	   return vehicleService.createVehicle(request);
   }
   
   @GetMapping
   public Page<VehicleResponseDTO> getAllVehicles(Pageable pageable) {
	   return vehicleService.getAllVehicles(pageable);
   }
   
   @GetMapping("/{id}")
   public VehicleResponseDTO getVehicleById(@PathVariable Long id) {
	   return vehicleService.getVehicleById(id);
   }
   
   @PutMapping("/{id}")
   public VehicleResponseDTO updateVehicle(@PathVariable Long id, @Valid @RequestBody VehicleRequestDTO request) {
	   return vehicleService.updateVehicle(id, request);
   }
   
   @DeleteMapping("/{id}")
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void deleteVehicle(@PathVariable Long id) {
	   vehicleService.deleteVehicle(id);
   }
   
}
