package com.fleetops.vehicle.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fleetops.common.exception.ResourceNotFoundException;
import com.fleetops.fleet.entity.Fleet;
import com.fleetops.fleet.repository.FleetRepository;
import com.fleetops.vehicle.dto.VehicleRequestDTO;
import com.fleetops.vehicle.dto.VehicleResponseDTO;
import com.fleetops.vehicle.entity.Vehicle;
import com.fleetops.vehicle.exception.VehicleAlreadyExistsException;
import com.fleetops.vehicle.mapper.VehicleMapper;
import com.fleetops.vehicle.repository.VehicleRepository;
import com.fleetops.vehicle.service.VehicleService;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {
	    private final VehicleRepository vehicleRepository;
	    private final FleetRepository fleetRepository;
	    private final VehicleMapper vehicleMapper;
	@Override
	public VehicleResponseDTO createVehicle(VehicleRequestDTO request) {
		if (vehicleRepository.existsByRegistrationNumber(
                request.getRegistrationNumber())) {

            throw new VehicleAlreadyExistsException(
                    "Vehicle with registration number already exists");
        }
		Fleet fleet = fleetRepository.findById(request.getFleetId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Fleet not found with id " + request.getFleetId()));

        Vehicle vehicle = vehicleMapper.toEntity(request, fleet);

        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        return vehicleMapper.toResponse(savedVehicle);
	}

	@Override
	public Page<VehicleResponseDTO> getAllVehicles(Pageable pageable) {
		 return vehicleRepository.findAll(pageable)
	                .map(vehicleMapper::toResponse);
	}

	@Override
	public VehicleResponseDTO getVehicleById(Long id) {
		Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Vehicle not found with id " + id));

        return vehicleMapper.toResponse(vehicle);
	}

	@Override
	public VehicleResponseDTO updateVehicle(Long id, VehicleRequestDTO request) {

	    Vehicle vehicle = vehicleRepository.findById(id)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Vehicle not found with id " + id));

	    if (!vehicle.getRegistrationNumber()
	            .equals(request.getRegistrationNumber())
	            && vehicleRepository.existsByRegistrationNumber(
	                    request.getRegistrationNumber())) {

	        throw new VehicleAlreadyExistsException(
	                "Vehicle with registration number already exists");
	    }

	    Fleet fleet = fleetRepository.findById(request.getFleetId())
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Fleet not found with id " + request.getFleetId()));

	    vehicle.setRegistrationNumber(request.getRegistrationNumber());
	    vehicle.setManufacturer(request.getManufacturer());
	    vehicle.setModel(request.getModel());
	    vehicle.setCapacity(request.getCapacity());
	    vehicle.setFleet(fleet);

	    Vehicle updatedVehicle = vehicleRepository.save(vehicle);

	    return vehicleMapper.toResponse(updatedVehicle);
	}

	@Override
	public void deleteVehicle(Long id) {

	    Vehicle vehicle = vehicleRepository.findById(id)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Vehicle not found with id " + id));

	    vehicleRepository.delete(vehicle);
	}
}
