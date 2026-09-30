package com.fleetops.dispatch.service.impl;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fleetops.common.exception.BusinessRuleViolationException;
import com.fleetops.common.exception.ResourceNotFoundException;
import com.fleetops.dispatch.dto.DispatchRequestDTO;
import com.fleetops.dispatch.dto.DispatchResponseDTO;
import com.fleetops.dispatch.entity.Dispatch;
import com.fleetops.dispatch.entity.DispatchStatus;
import com.fleetops.dispatch.mapper.DispatchMapper;
import com.fleetops.dispatch.repository.DispatchRepository;
import com.fleetops.dispatch.service.DispatchService;
import com.fleetops.shipment.entity.Shipment;
import com.fleetops.shipment.entity.ShipmentStatus;
import com.fleetops.shipment.repository.ShipmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DispatchServiceImpl implements DispatchService {

    private final DispatchRepository dispatchRepository;
    private final ShipmentRepository shipmentRepository;
    private final DispatchMapper dispatchMapper;

    @Override
    @Transactional
    public DispatchResponseDTO createDispatch(
            DispatchRequestDTO request) {

        Shipment shipment = shipmentRepository.findById(
                request.getShipmentId())
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "Shipment not found with id: "
                    + request.getShipmentId()));

        /*
         * A shipment must first be assigned
         * before it can be dispatched.
         */
        if (shipment.getStatus() != ShipmentStatus.ASSIGNED) {
            throw new BusinessRuleViolationException(
                "Only ASSIGNED shipments can have a dispatch created");
        }

        /*
         * Prevent multiple dispatch records
         * for the same shipment.
         */
        if (dispatchRepository.existsByShipmentId(
                request.getShipmentId())) {

            throw new BusinessRuleViolationException(
                "A dispatch already exists for shipment: "
                + request.getShipmentId());
        }

        Dispatch dispatch =
                dispatchMapper.toEntity(request, shipment);

        dispatch.setStatus(DispatchStatus.PENDING);

        Dispatch savedDispatch =
                dispatchRepository.save(dispatch);

        return dispatchMapper.toResponse(savedDispatch);
    }

    @Override
    public Page<DispatchResponseDTO> getAllDispatches(
            Pageable pageable) {

        return dispatchRepository
                .findAll(pageable)
                .map(dispatchMapper::toResponse);
    }

    @Override
    public DispatchResponseDTO getDispatchById(Long id) {

        Dispatch dispatch = dispatchRepository.findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "Dispatch not found with id: " + id));

        return dispatchMapper.toResponse(dispatch);
    }

    @Override
    @Transactional
    public DispatchResponseDTO startDispatch(Long id) {

        Dispatch dispatch = dispatchRepository.findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "Dispatch not found with id: " + id));

        if (dispatch.getStatus() != DispatchStatus.PENDING) {
            throw new BusinessRuleViolationException(
                "Only PENDING dispatches can be started");
        }

        Shipment shipment = dispatch.getShipment();

        if (shipment.getStatus() != ShipmentStatus.ASSIGNED) {
            throw new BusinessRuleViolationException(
                "Only ASSIGNED shipments can be dispatched");
        }

        dispatch.setStatus(DispatchStatus.DISPATCHED);
        dispatch.setDispatchTime(LocalDateTime.now());

        shipment.setStatus(ShipmentStatus.IN_TRANSIT);

        shipmentRepository.save(shipment);
        dispatchRepository.save(dispatch);

        return dispatchMapper.toResponse(dispatch);
    }

    @Override
    @Transactional
    public DispatchResponseDTO completeDispatch(Long id) {

        Dispatch dispatch = dispatchRepository.findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "Dispatch not found with id: " + id));

        if (dispatch.getStatus() != DispatchStatus.DISPATCHED) {
            throw new BusinessRuleViolationException(
                "Only DISPATCHED dispatches can be completed");
        }

        Shipment shipment = dispatch.getShipment();

        if (shipment.getStatus() != ShipmentStatus.IN_TRANSIT) {
            throw new BusinessRuleViolationException(
                "Only IN_TRANSIT shipments can be completed");
        }

        dispatch.setStatus(DispatchStatus.COMPLETED);
        dispatch.setActualDelivery(LocalDateTime.now());

        shipment.setStatus(ShipmentStatus.DELIVERED);

        /*
         * Release the driver and vehicle after delivery.
         */
        if (shipment.getDriver() != null) {
            shipment.getDriver().setStatus(
                com.fleetops.driver.entity.DriverStatus.AVAILABLE
            );
        }

        if (shipment.getVehicle() != null) {
            shipment.getVehicle().setStatus(
                com.fleetops.vehicle.entity.VehicleStatus.AVAILABLE
            );
        }

        shipmentRepository.save(shipment);
        dispatchRepository.save(dispatch);

        return dispatchMapper.toResponse(dispatch);
    }

    @Override
    @Transactional
    public DispatchResponseDTO cancelDispatch(Long id) {

        Dispatch dispatch = dispatchRepository.findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "Dispatch not found with id: " + id));

        if (dispatch.getStatus() != DispatchStatus.PENDING) {
            throw new BusinessRuleViolationException(
                "Only PENDING dispatches can be cancelled");
        }

        Shipment shipment = dispatch.getShipment();

        /*
         * Since dispatch has not started, shipment
         * can return to CREATED.
         */
        if (shipment.getStatus() == ShipmentStatus.ASSIGNED) {

            shipment.setStatus(ShipmentStatus.CREATED);

            if (shipment.getDriver() != null) {
                shipment.getDriver().setStatus(
                    com.fleetops.driver.entity.DriverStatus.AVAILABLE
                );
            }

            if (shipment.getVehicle() != null) {
                shipment.getVehicle().setStatus(
                    com.fleetops.vehicle.entity.VehicleStatus.AVAILABLE
                );
            }

            shipmentRepository.save(shipment);
        }

        dispatch.setStatus(DispatchStatus.CANCELLED);

        Dispatch savedDispatch =
                dispatchRepository.save(dispatch);

        return dispatchMapper.toResponse(savedDispatch);
    }
}