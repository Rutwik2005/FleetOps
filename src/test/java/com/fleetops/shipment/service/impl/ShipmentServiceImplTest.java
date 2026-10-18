
package com.fleetops.shipment.service.impl;

import com.fleetops.common.exception.BusinessRuleViolationException;
import com.fleetops.common.exception.ResourceNotFoundException;
import com.fleetops.driver.entity.Driver;
import com.fleetops.driver.entity.DriverStatus;
import com.fleetops.driver.repository.DriverRepository;
import com.fleetops.event.dto.ShipmentEvent;
import com.fleetops.event.producer.ShipmentEventProducer;
import com.fleetops.shipment.dto.ShipmentResponseDTO;
import com.fleetops.shipment.entity.Shipment;
import com.fleetops.shipment.entity.ShipmentStatus;
import com.fleetops.shipment.mapper.ShipmentMapper;
import com.fleetops.shipment.repository.ShipmentRepository;
import com.fleetops.vehicle.entity.Vehicle;
import com.fleetops.vehicle.entity.VehicleStatus;
import com.fleetops.vehicle.repository.VehicleRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShipmentServiceImplTest {

    @Mock
    private ShipmentMapper shipmentMapper;

    @Mock
    private ShipmentRepository shipmentRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private DriverRepository driverRepository;

    @Mock
    private ShipmentEventProducer shipmentEventProducer;

    @InjectMocks
    private ShipmentServiceImpl shipmentService;

    private Shipment shipment;
    private Driver driver;
    private Vehicle vehicle;
    private ShipmentResponseDTO response;

    @BeforeEach
    void setUp() {
        driver = new Driver();
        driver.setId(1L);
        driver.setFirstName("Rahul");
        driver.setLastName("Sharma");
        driver.setStatus(DriverStatus.AVAILABLE);

        vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setRegistrationNumber("MH31AB1001");
        vehicle.setStatus(VehicleStatus.AVAILABLE);

        shipment = new Shipment();
        shipment.setId(1L);
        shipment.setShipmentNumber("SHP-TEST-1001");
        shipment.setSource("Nagpur");
        shipment.setDestination("Pune");
        shipment.setCargoDescription("Electronics");
        shipment.setWeight(500.0);
        shipment.setStatus(ShipmentStatus.CREATED);
        shipment.setDriver(driver);
        shipment.setVehicle(vehicle);

        response = new ShipmentResponseDTO();
        response.setId(1L);
        response.setShipmentNumber("SHP-TEST-1001");
    }

    @Test
    void assignShipment_shouldAssignShipmentAndMarkResourcesBusy() {
        when(shipmentRepository.findById(1L))
                .thenReturn(Optional.of(shipment));
        when(shipmentRepository.save(shipment))
                .thenReturn(shipment);
        when(shipmentMapper.toResponse(shipment))
                .thenReturn(response);

        ShipmentResponseDTO result =
                shipmentService.assignShipment(1L);

        assertEquals(ShipmentStatus.ASSIGNED, shipment.getStatus());
        assertEquals(DriverStatus.ON_TRIP, driver.getStatus());
        assertEquals(VehicleStatus.IN_TRANSIT, vehicle.getStatus());
        assertEquals("SHP-TEST-1001", result.getShipmentNumber());

        verify(driverRepository).save(driver);
        verify(vehicleRepository).save(vehicle);
        verify(shipmentRepository).save(shipment);
        verify(shipmentEventProducer)
                .publishShipmentEvent(any(ShipmentEvent.class));
    }

    @Test
    void assignShipment_shouldRejectShipmentNotInCreatedStatus() {
        shipment.setStatus(ShipmentStatus.ASSIGNED);

        when(shipmentRepository.findById(1L))
                .thenReturn(Optional.of(shipment));

        assertThrows(
                BusinessRuleViolationException.class,
                () -> shipmentService.assignShipment(1L)
        );

        verify(shipmentRepository, never()).save(any(Shipment.class));
        verifyNoInteractions(shipmentEventProducer);
    }

    @Test
    void assignShipment_shouldRejectUnavailableDriver() {
        driver.setStatus(DriverStatus.ON_TRIP);

        when(shipmentRepository.findById(1L))
                .thenReturn(Optional.of(shipment));

        assertThrows(
                BusinessRuleViolationException.class,
                () -> shipmentService.assignShipment(1L)
        );

        verify(shipmentRepository, never()).save(any(Shipment.class));
    }

    @Test
    void dispatchShipment_shouldMoveAssignedShipmentToInTransit() {
        shipment.setStatus(ShipmentStatus.ASSIGNED);

        when(shipmentRepository.findById(1L))
                .thenReturn(Optional.of(shipment));
        when(shipmentRepository.save(shipment))
                .thenReturn(shipment);
        when(shipmentMapper.toResponse(shipment))
                .thenReturn(response);

        ShipmentResponseDTO result =
                shipmentService.dispatchShipment(1L);

        assertEquals(ShipmentStatus.IN_TRANSIT, shipment.getStatus());
        assertNotNull(shipment.getDispatchDate());
        assertEquals("SHP-TEST-1001", result.getShipmentNumber());

        verify(shipmentEventProducer)
                .publishShipmentEvent(any(ShipmentEvent.class));
    }

    @Test
    void dispatchShipment_shouldRejectCreatedShipment() {
        shipment.setStatus(ShipmentStatus.CREATED);

        when(shipmentRepository.findById(1L))
                .thenReturn(Optional.of(shipment));

        assertThrows(
                BusinessRuleViolationException.class,
                () -> shipmentService.dispatchShipment(1L)
        );

        verify(shipmentRepository, never()).save(any(Shipment.class));
    }

    @Test
    void completeShipment_shouldDeliverShipmentAndReleaseResources() {
        shipment.setStatus(ShipmentStatus.IN_TRANSIT);
        driver.setStatus(DriverStatus.ON_TRIP);
        vehicle.setStatus(VehicleStatus.IN_TRANSIT);

        when(shipmentRepository.findById(1L))
                .thenReturn(Optional.of(shipment));
        when(shipmentRepository.save(shipment))
                .thenReturn(shipment);
        when(shipmentMapper.toResponse(shipment))
                .thenReturn(response);

        shipmentService.completeShipment(1L);

        assertEquals(ShipmentStatus.DELIVERED, shipment.getStatus());
        assertEquals(DriverStatus.AVAILABLE, driver.getStatus());
        assertEquals(VehicleStatus.AVAILABLE, vehicle.getStatus());
        assertNotNull(shipment.getDeliveryDate());

        verify(driverRepository).save(driver);
        verify(vehicleRepository).save(vehicle);
        verify(shipmentEventProducer)
                .publishShipmentEvent(any(ShipmentEvent.class));
    }

    @Test
    void completeShipment_shouldRejectShipmentNotInTransit() {
        shipment.setStatus(ShipmentStatus.ASSIGNED);

        when(shipmentRepository.findById(1L))
                .thenReturn(Optional.of(shipment));

        assertThrows(
                BusinessRuleViolationException.class,
                () -> shipmentService.completeShipment(1L)
        );

        verify(shipmentRepository, never()).save(any(Shipment.class));
        verifyNoInteractions(shipmentEventProducer);
    }

    @Test
    void cancelShipment_shouldCancelAssignedShipmentAndReleaseResources() {
        shipment.setStatus(ShipmentStatus.ASSIGNED);
        driver.setStatus(DriverStatus.ON_TRIP);
        vehicle.setStatus(VehicleStatus.IN_TRANSIT);

        when(shipmentRepository.findById(1L))
                .thenReturn(Optional.of(shipment));
        when(shipmentRepository.save(shipment))
                .thenReturn(shipment);
        when(shipmentMapper.toResponse(shipment))
                .thenReturn(response);

        shipmentService.cancelShipment(1L);

        assertEquals(ShipmentStatus.CANCELLED, shipment.getStatus());
        assertEquals(DriverStatus.AVAILABLE, driver.getStatus());
        assertEquals(VehicleStatus.AVAILABLE, vehicle.getStatus());

        verify(driverRepository).save(driver);
        verify(vehicleRepository).save(vehicle);
        verify(shipmentEventProducer)
                .publishShipmentEvent(any(ShipmentEvent.class));
    }

    @Test
    void cancelShipment_shouldRejectShipmentAlreadyInTransit() {
        shipment.setStatus(ShipmentStatus.IN_TRANSIT);

        when(shipmentRepository.findById(1L))
                .thenReturn(Optional.of(shipment));

        assertThrows(
                BusinessRuleViolationException.class,
                () -> shipmentService.cancelShipment(1L)
        );

        verify(shipmentRepository, never()).save(any(Shipment.class));
        verifyNoInteractions(shipmentEventProducer);
    }

    @Test
    void getShipmentById_shouldThrowWhenShipmentDoesNotExist() {
        when(shipmentRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> shipmentService.getShipmentById(999L)
        );

        verify(shipmentMapper, never()).toResponse(any(Shipment.class));
    }

@Test
void updateShipment_shouldRejectAssignedShipment() {
    shipment.setStatus(ShipmentStatus.ASSIGNED);

    when(shipmentRepository.findById(1L))
            .thenReturn(Optional.of(shipment));

    assertThrows(
            BusinessRuleViolationException.class,
            () -> shipmentService.updateShipment(
                    1L, new com.fleetops.shipment.dto.ShipmentRequestDTO()
            )
    );

    verify(shipmentRepository, never()).save(any(Shipment.class));
    verifyNoInteractions(shipmentMapper);
}

@Test
void updateShipment_shouldRejectDeliveredShipment() {
    shipment.setStatus(ShipmentStatus.DELIVERED);

    when(shipmentRepository.findById(1L))
            .thenReturn(Optional.of(shipment));

    assertThrows(
            BusinessRuleViolationException.class,
            () -> shipmentService.updateShipment(
                    1L, new com.fleetops.shipment.dto.ShipmentRequestDTO()
            )
    );

    verify(shipmentRepository, never()).save(any(Shipment.class));
    verifyNoInteractions(shipmentMapper);
}

@Test
void deleteShipment_shouldDeleteCreatedShipment() {
    shipment.setStatus(ShipmentStatus.CREATED);

    when(shipmentRepository.findById(1L))
            .thenReturn(Optional.of(shipment));

    assertDoesNotThrow(() -> shipmentService.deleteShipment(1L));

    verify(shipmentRepository).delete(shipment);
}

@Test
void deleteShipment_shouldRejectCancelledShipment() {
    shipment.setStatus(ShipmentStatus.CANCELLED);

    when(shipmentRepository.findById(1L))
            .thenReturn(Optional.of(shipment));

    assertThrows(
            BusinessRuleViolationException.class,
            () -> shipmentService.deleteShipment(1L)
    );

    verify(shipmentRepository, never()).delete(any(Shipment.class));
}

}
