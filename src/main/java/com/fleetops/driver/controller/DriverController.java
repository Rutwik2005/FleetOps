package com.fleetops.driver.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

import com.fleetops.driver.dto.DriverRequestDTO;
import com.fleetops.driver.dto.DriverResponseDTO;
import com.fleetops.driver.service.DriverService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
@Validated
public class DriverController {

    private final DriverService driverService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DriverResponseDTO createDriver(
            @Valid @RequestBody DriverRequestDTO request) {

        return driverService.createDriver(request);
    }

    @GetMapping
    public Page<DriverResponseDTO> getAllDrivers(Pageable pageable) {

        return driverService.getAllDrivers(pageable);
    }

    @GetMapping("/{id}")
    public DriverResponseDTO getDriverById(
            @PathVariable Long id) {

        return driverService.getDriverById(id);
    }

    @PutMapping("/{id}")
    public DriverResponseDTO updateDriver(
            @PathVariable Long id,
            @Valid @RequestBody DriverRequestDTO request) {

        return driverService.updateDriver(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDriver(
            @PathVariable Long id) {

        driverService.deleteDriver(id);
    }

}