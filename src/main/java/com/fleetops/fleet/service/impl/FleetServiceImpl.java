package com.fleetops.fleet.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fleetops.common.exception.FleetAlreadyExistsException;
import com.fleetops.common.exception.ResourceNotFoundException;
import com.fleetops.fleet.dto.FleetRequestDTO;
import com.fleetops.fleet.dto.FleetResponseDTO;
import com.fleetops.fleet.entity.Fleet;
import com.fleetops.fleet.mapper.FleetMapper;
import com.fleetops.fleet.repository.FleetRepository;
import com.fleetops.fleet.service.FleetService;
@Service
public class FleetServiceImpl implements FleetService {
  private final FleetRepository fleetRepository;
  private final FleetMapper fleetMapper;
  public FleetServiceImpl(FleetRepository fleetRepository, FleetMapper fleetMapper) {
	this.fleetRepository = fleetRepository;
	this.fleetMapper = fleetMapper;
  }
  @Override
  public FleetResponseDTO createFleet(FleetRequestDTO request) {
	if(fleetRepository.existsByContactEmail(request.getContactEmail())) {
		throw new FleetAlreadyExistsException("Fleet already exists with this contact email.");
	}
	Fleet fleet = fleetMapper.toEntity(request);
	
	Fleet savedFleet = fleetRepository.save(fleet);
	
	return fleetMapper.toResponse(savedFleet);
  }
  @Override
  public List<FleetResponseDTO> getAllFleets() {
	return fleetRepository.findAll().stream()
			.map(fleetMapper::toResponse)
			.collect(Collectors.toList());
  }
  @Override
  public FleetResponseDTO getFleetById(Long id) {
	Fleet fleet = fleetRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Fleet not found with id: " + id));
    return fleetMapper.toResponse(fleet);
  }
  @Override
  public FleetResponseDTO updateFleet(Long id, FleetRequestDTO request) {
	Fleet fleet = fleetRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Fleet not found with id: " + id));
	
	fleet.setFleetName(request.getFleetName());
	fleet.setCompanyName(request.getCompanyName());
	fleet.setHeadOffice(request.getHeadOffice());
	fleet.setContactEmail(request.getContactEmail());
	fleet.setContactPhone(request.getContactPhone());
	
	Fleet updatedFleet = fleetRepository.save(fleet);
	return fleetMapper.toResponse(updatedFleet);
  }
  @Override
  public void deleteFleet(Long id) {
	Fleet fleet = fleetRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Fleet not found with id: " + id));
	
	fleetRepository.delete(fleet);
	
  }
  
  
}
