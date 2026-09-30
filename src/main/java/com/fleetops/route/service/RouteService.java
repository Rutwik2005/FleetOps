package com.fleetops.route.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fleetops.route.dto.RouteRequestDTO;
import com.fleetops.route.dto.RouteResponseDTO;

public interface RouteService {

    RouteResponseDTO createRoute(RouteRequestDTO request);

    Page<RouteResponseDTO> getAllRoutes(Pageable pageable);

    RouteResponseDTO getRouteById(Long id);

    RouteResponseDTO startRoute(Long id);

    RouteResponseDTO completeRoute(Long id);

    RouteResponseDTO cancelRoute(Long id);
}