package com.fleetops.route.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.fleetops.route.dto.RouteRequestDTO;
import com.fleetops.route.dto.RouteResponseDTO;
import com.fleetops.route.service.RouteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/routes")
@RequiredArgsConstructor
@Validated
public class RouteController {

    private final RouteService routeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RouteResponseDTO createRoute(
            @Valid @RequestBody RouteRequestDTO request) {

        return routeService.createRoute(request);
    }

    @GetMapping
    public Page<RouteResponseDTO> getAllRoutes(
            Pageable pageable) {

        return routeService.getAllRoutes(pageable);
    }

    @GetMapping("/{id}")
    public RouteResponseDTO getRouteById(
            @PathVariable Long id) {

        return routeService.getRouteById(id);
    }

    @PostMapping("/{id}/start")
    public RouteResponseDTO startRoute(
            @PathVariable Long id) {

        return routeService.startRoute(id);
    }

    @PostMapping("/{id}/complete")
    public RouteResponseDTO completeRoute(
            @PathVariable Long id) {

        return routeService.completeRoute(id);
    }

    @PostMapping("/{id}/cancel")
    public RouteResponseDTO cancelRoute(
            @PathVariable Long id) {

        return routeService.cancelRoute(id);
    }
}