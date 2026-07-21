package com.fleetops.driver.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fleetops.common.exception.DuplicateResourceException;
import com.fleetops.common.exception.ResourceNotFoundException;
import com.fleetops.driver.dto.DriverRequestDTO;
import com.fleetops.driver.dto.DriverResponseDTO;
import com.fleetops.driver.entity.Driver;
import com.fleetops.driver.mapper.DriverMapper;
import com.fleetops.driver.repository.DriverRepository;
import com.fleetops.driver.service.DriverService;
import com.fleetops.fleet.entity.Fleet;
import com.fleetops.fleet.repository.FleetRepository;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor

public class DriverServiceImpl implements DriverService {
	private final DriverRepository driverRepository;
	private final FleetRepository fleetRepository;
	private final DriverMapper driverMapper;
	@Override
	public DriverResponseDTO createDriver(DriverRequestDTO request) {

	    if (driverRepository.existsByLicenseNumber(request.getLicenseNumber())) {
	        throw new DuplicateResourceException(
	                "Driver with license number " + request.getLicenseNumber() + " already exists");
	    }

	    if (driverRepository.existsByPhoneNumber(request.getPhoneNumber())) {
	        throw new DuplicateResourceException(
	                "Driver with phone number " + request.getPhoneNumber() + " already exists");
	    }

	    Fleet fleet = fleetRepository.findById(request.getFleetId())
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Fleet not found with id " + request.getFleetId()));

	    Driver driver = driverMapper.toEntity(request, fleet);

	    Driver savedDriver = driverRepository.save(driver);

	    return driverMapper.toResponse(savedDriver);
	}

	@Override
	public Page<DriverResponseDTO> getAllDrivers(Pageable pageable) {
		return driverRepository.findAll(pageable)
				.map(driverMapper::toResponse);
	}

	@Override
	public DriverResponseDTO getDriverById(Long id) {
		
	    Driver driver = driverRepository.findById(id)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException("Driver not found with id " + id));

	    return driverMapper.toResponse(driver);
	}

	@Override
	public DriverResponseDTO updateDriver(Long id, DriverRequestDTO request) {

	    Driver driver = driverRepository.findById(id)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Driver not found with id " + id));

	    if (!driver.getLicenseNumber().equals(request.getLicenseNumber())
	            && driverRepository.existsByLicenseNumber(request.getLicenseNumber())) {

	        throw new DuplicateResourceException(
	                "Driver with license number "
	                        + request.getLicenseNumber()
	                        + " already exists");
	    }

	    if (!driver.getPhoneNumber().equals(request.getPhoneNumber())
	            && driverRepository.existsByPhoneNumber(request.getPhoneNumber())) {

	        throw new DuplicateResourceException(
	                "Driver with phone number "
	                        + request.getPhoneNumber()
	                        + " already exists");
	    }

	    Fleet fleet = fleetRepository.findById(request.getFleetId())
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Fleet not found with id "
	                                    + request.getFleetId()));

	    driver.setFirstName(request.getFirstName());
	    driver.setLastName(request.getLastName());
	    driver.setLicenseNumber(request.getLicenseNumber());
	    driver.setPhoneNumber(request.getPhoneNumber());
	    driver.setFleet(fleet);

	    Driver updatedDriver = driverRepository.save(driver);

	    return driverMapper.toResponse(updatedDriver);

	}

	@Override
	public void deleteDriver(Long id) {

	    Driver driver = driverRepository.findById(id)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Driver not found with id " + id));

	    driverRepository.delete(driver);

	}

}
