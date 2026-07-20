package com.fleetops.vehicle.mapper;

import org.springframework.stereotype.Component;

import com.fleetops.fleet.entity.Fleet;
import com.fleetops.vehicle.dto.VehicleRequestDTO;
import com.fleetops.vehicle.dto.VehicleResponseDTO;
import com.fleetops.vehicle.entity.Vehicle;

@Component
public class VehicleMapper {
    public Vehicle toEntity(VehicleRequestDTO request,Fleet fleet) {
    	Vehicle vehicle = new Vehicle();
    	vehicle.setRegistrationNumber(request.getRegistrationNumber());
    	vehicle.setManufacturer(request.getManufacturer());
    	vehicle.setModel(request.getModel());
    	vehicle.setCapacity(request.getCapacity());
    	
    	vehicle.setFleet(fleet);
    	
    	return vehicle;
    }
    
    public VehicleResponseDTO toResponse(Vehicle vehicle) {
      VehicleResponseDTO response = new VehicleResponseDTO();
      
        response.setId(vehicle.getId());
        response.setRegistrationNumber(vehicle.getRegistrationNumber());
        response.setManufacturer(vehicle.getManufacturer());
        response.setModel(vehicle.getModel());
        response.setCapacity(vehicle.getCapacity());
        response.setStatus(vehicle.getStatus());
        response.setCreatedAt(vehicle.getCreatedAt());
        
        response.setFleetId(vehicle.getFleet().getId());
        response.setFleetName(vehicle.getFleet().getFleetName());
        
        return response;
    }
}
