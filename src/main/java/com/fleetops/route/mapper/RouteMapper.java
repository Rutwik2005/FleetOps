package com.fleetops.route.mapper;

import org.springframework.stereotype.Component;

import com.fleetops.route.dto.RouteRequestDTO;
import com.fleetops.route.dto.RouteResponseDTO;
import com.fleetops.route.entity.Route;
import com.fleetops.shipment.entity.Shipment;

@Component
public class RouteMapper {

    public Route toEntity(
            RouteRequestDTO request,
            Shipment shipment) {

        Route route = new Route();

        route.setShipment(shipment);
        route.setSource(shipment.getSource());
        route.setDestination(shipment.getDestination());

        route.setDistanceKm(
            request.getDistanceKm()
        );

        route.setEstimatedDurationMinutes(
            request.getEstimatedDurationMinutes()
        );

        return route;
    }

    public RouteResponseDTO toResponse(Route route) {

        RouteResponseDTO response =
                new RouteResponseDTO();

        response.setId(route.getId());

        if (route.getShipment() != null) {

            response.setShipmentId(
                route.getShipment().getId()
            );

            response.setShipmentNumber(
                route.getShipment().getShipmentNumber()
            );
        }

        response.setSource(route.getSource());
        response.setDestination(route.getDestination());
        response.setDistanceKm(route.getDistanceKm());

        response.setEstimatedDurationMinutes(
            route.getEstimatedDurationMinutes()
        );

        response.setStatus(route.getStatus());
        response.setStartedAt(route.getStartedAt());
        response.setCompletedAt(route.getCompletedAt());
        response.setCreatedAt(route.getCreatedAt());

        return response;
    }
}