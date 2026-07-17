package com.fleetops.fleet.mapper;

import org.springframework.stereotype.Component;

import com.fleetops.fleet.dto.FleetRequestDTO;
import com.fleetops.fleet.dto.FleetResponseDTO;
import com.fleetops.fleet.entity.Fleet;

@Component
public class FleetMapper {
  public Fleet toEntity(FleetRequestDTO request)
  {
	  Fleet fleet = new Fleet();
	  fleet.setFleetName(request.getFleetName());
	  fleet.setCompanyName(request.getCompanyName());
	  fleet.setHeadOffice(request.getHeadOffice());
	  fleet.setContactEmail(request.getContactEmail());
	  fleet.setContactPhone(request.getContactPhone());
	  return fleet;
  }
  public FleetResponseDTO toResponse(Fleet fleet) {

      FleetResponseDTO response = new FleetResponseDTO();

      response.setId(fleet.getId());
      response.setFleetName(fleet.getFleetName());
      response.setCompanyName(fleet.getCompanyName());
      response.setHeadOffice(fleet.getHeadOffice());
      response.setContactEmail(fleet.getContactEmail());
      response.setContactPhone(fleet.getContactPhone());
      response.setStatus(fleet.getStatus());
      response.setCreatedAt(fleet.getCreatedAt());

      return response;
  }
}
