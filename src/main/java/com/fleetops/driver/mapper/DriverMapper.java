package com.fleetops.driver.mapper;

import org.springframework.stereotype.Component;

import com.fleetops.driver.dto.DriverRequestDTO;
import com.fleetops.driver.dto.DriverResponseDTO;
import com.fleetops.driver.entity.Driver;
import com.fleetops.fleet.entity.Fleet;

@Component
public class DriverMapper {

    public Driver toEntity(DriverRequestDTO request, Fleet fleet) {

        Driver driver = new Driver();

        driver.setFirstName(request.getFirstName());
        driver.setLastName(request.getLastName());
        driver.setLicenseNumber(request.getLicenseNumber());
        driver.setPhoneNumber(request.getPhoneNumber());
        driver.setFleet(fleet);

        return driver;
    }

    public DriverResponseDTO toResponse(Driver driver) {

        DriverResponseDTO response = new DriverResponseDTO();

        response.setId(driver.getId());
        response.setFirstName(driver.getFirstName());
        response.setLastName(driver.getLastName());
        response.setLicenseNumber(driver.getLicenseNumber());
        response.setPhoneNumber(driver.getPhoneNumber());
        response.setStatus(driver.getStatus());
        response.setFleetId(driver.getFleet().getId());
        response.setFleetName(driver.getFleet().getFleetName());
        response.setCreatedAt(driver.getCreatedAt());

        return response;
    }
}