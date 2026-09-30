package com.fleetops.route.service.impl;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fleetops.common.exception.BusinessRuleViolationException;
import com.fleetops.common.exception.ResourceNotFoundException;
import com.fleetops.route.dto.RouteRequestDTO;
import com.fleetops.route.dto.RouteResponseDTO;
import com.fleetops.route.entity.Route;
import com.fleetops.route.entity.RouteStatus;
import com.fleetops.route.mapper.RouteMapper;
import com.fleetops.route.repository.RouteRepository;
import com.fleetops.route.service.RouteService;
import com.fleetops.shipment.entity.Shipment;
import com.fleetops.shipment.entity.ShipmentStatus;
import com.fleetops.shipment.repository.ShipmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;

    private final ShipmentRepository shipmentRepository;

    private final RouteMapper routeMapper;

    @Override
    @Transactional
    public RouteResponseDTO createRoute(
            RouteRequestDTO request) {

        Shipment shipment =
                shipmentRepository.findById(
                    request.getShipmentId()
                )
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Shipment not found with id: "
                        + request.getShipmentId()
                    )
                );

        if (routeRepository.existsByShipmentId(
                request.getShipmentId())) {

            throw new BusinessRuleViolationException(
                "A route already exists for shipment: "
                + request.getShipmentId()
            );
        }

        if (shipment.getStatus() != ShipmentStatus.ASSIGNED) {

            throw new BusinessRuleViolationException(
                "Route can only be created for an ASSIGNED shipment"
            );
        }

        Route route =
                routeMapper.toEntity(
                    request,
                    shipment
                );

        route.setStatus(RouteStatus.PLANNED);

        Route savedRoute =
                routeRepository.save(route);

        return routeMapper.toResponse(savedRoute);
    }

    @Override
    public Page<RouteResponseDTO> getAllRoutes(
            Pageable pageable) {

        return routeRepository
                .findAll(pageable)
                .map(routeMapper::toResponse);
    }

    @Override
    public RouteResponseDTO getRouteById(Long id) {

        Route route =
                routeRepository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Route not found with id: " + id
                    )
                );

        return routeMapper.toResponse(route);
    }

    @Override
    @Transactional
    public RouteResponseDTO startRoute(Long id) {

        Route route =
                routeRepository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Route not found with id: " + id
                    )
                );

        if (route.getStatus() != RouteStatus.PLANNED) {

            throw new BusinessRuleViolationException(
                "Only PLANNED routes can be started"
            );
        }

        Shipment shipment = route.getShipment();

        if (shipment.getStatus() != ShipmentStatus.ASSIGNED) {

            throw new BusinessRuleViolationException(
                "Only ASSIGNED shipments can start a route"
            );
        }

        route.setStatus(RouteStatus.ACTIVE);
        route.setStartedAt(LocalDateTime.now());

        Route savedRoute =
                routeRepository.save(route);

        return routeMapper.toResponse(savedRoute);
    }

    @Override
    @Transactional
    public RouteResponseDTO completeRoute(Long id) {

        Route route =
                routeRepository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Route not found with id: " + id
                    )
                );

        if (route.getStatus() != RouteStatus.ACTIVE) {

            throw new BusinessRuleViolationException(
                "Only ACTIVE routes can be completed"
            );
        }

        route.setStatus(RouteStatus.COMPLETED);
        route.setCompletedAt(LocalDateTime.now());

        Route savedRoute =
                routeRepository.save(route);

        return routeMapper.toResponse(savedRoute);
    }

    @Override
    @Transactional
    public RouteResponseDTO cancelRoute(Long id) {

        Route route =
                routeRepository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Route not found with id: " + id
                    )
                );

        if (route.getStatus() != RouteStatus.PLANNED) {

            throw new BusinessRuleViolationException(
                "Only PLANNED routes can be cancelled"
            );
        }

        route.setStatus(RouteStatus.CANCELLED);

        Route savedRoute =
                routeRepository.save(route);

        return routeMapper.toResponse(savedRoute);
    }
}