package com.fleetops.shipment.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fleetops.common.exception.BusinessRuleViolationException;
import com.fleetops.common.exception.DuplicateResourceException;
import com.fleetops.common.exception.ResourceNotFoundException;
import com.fleetops.driver.entity.Driver;
import com.fleetops.driver.entity.DriverStatus;
import com.fleetops.driver.repository.DriverRepository;
import com.fleetops.event.producer.ShipmentEventProducer;
import com.fleetops.shipment.dto.ShipmentRequestDTO;
import com.fleetops.shipment.dto.ShipmentResponseDTO;
import com.fleetops.shipment.entity.Shipment;
import com.fleetops.shipment.entity.ShipmentStatus;
import com.fleetops.shipment.mapper.ShipmentMapper;
import com.fleetops.shipment.repository.ShipmentRepository;
import com.fleetops.shipment.service.ShipmentService;
import com.fleetops.vehicle.entity.Vehicle;
import com.fleetops.vehicle.entity.VehicleStatus;
import com.fleetops.vehicle.repository.VehicleRepository;
import com.fleetops.event.dto.ShipmentEvent;
import com.fleetops.event.dto.ShipmentEventType;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ShipmentServiceImpl  implements ShipmentService {
    private final ShipmentMapper shipmentMapper;
    private final ShipmentRepository shipmentRepository;
    private final VehicleRepository vehicleRepository;
    private final DriverRepository driverRepository;
    private final ShipmentEventProducer shipmentEventProducer;
	@Override
	public ShipmentResponseDTO createShipment(ShipmentRequestDTO request) {
		if(shipmentRepository.existsByShipmentNumber(request.getShipmentNumber())) {
			throw new DuplicateResourceException("Shipment with  number"+request.getShipmentNumber() + "already exists");
		}
		
		if(request.getSource().equalsIgnoreCase(request.getDestination())) {
			throw new BusinessRuleViolationException(
			        "Source and destination cannot be the same");
		}
		
		Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
				.orElseThrow(() -> new ResourceNotFoundException("Vehicle with id " + request.getVehicleId() + " not found"));
		
		Driver driver = driverRepository.findById(request.getDriverId())
				.orElseThrow(() -> new ResourceNotFoundException("Driver with id " + request.getDriverId() + " not found"));
		
		if(vehicle.getStatus() != VehicleStatus.AVAILABLE) {
			throw new BusinessRuleViolationException(
			        "Vehicle with id " + request.getVehicleId() + " is not available");
		}
		
		if (driver.getStatus() != DriverStatus.AVAILABLE) {
			throw new BusinessRuleViolationException(
			        "Driver is not available");
	    }

	    if (!vehicle.getFleet().getId().equals(driver.getFleet().getId())) {
	    	throw new BusinessRuleViolationException(
	    	        "Driver and Vehicle must belong to the same Fleet");
	    }
		
	    Shipment shipment =
	            shipmentMapper.toEntity(request, vehicle, driver);

	    Shipment savedShipment =
	            shipmentRepository.save(shipment);

	    return shipmentMapper.toResponse(savedShipment);
	    
	}

	@Override
	public Page<ShipmentResponseDTO> getAllShipments(Pageable pageable) {
		 return shipmentRepository.findAll(pageable)
		            .map(shipmentMapper::toResponse);
	}

	@Override
	public ShipmentResponseDTO getShipmentById(Long id) {

	    Shipment shipment = shipmentRepository.findById(id)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Shipment not found with id " + id));

	    return shipmentMapper.toResponse(shipment);
	}

	@Override
	public ShipmentResponseDTO updateShipment(Long id, ShipmentRequestDTO request) {
		Shipment shipment = shipmentRepository.findById(id)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Shipment not found with id " + id));
        if(!shipment.getShipmentNumber().equals(request.getShipmentNumber()) && shipmentRepository.existsByShipmentNumber(request.getShipmentNumber())) {
			throw new DuplicateResourceException("Shipment with  number"+request.getShipmentNumber() + "already exists");
		}
        
        if (request.getSource().equalsIgnoreCase(request.getDestination())) {
        	throw new BusinessRuleViolationException(
        	        "Source and destination cannot be the same");
        }  
        
        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Vehicle not found with id " + request.getVehicleId()));
        
        Driver driver = driverRepository.findById(request.getDriverId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Driver not found with id " + request.getDriverId()));
       
        if (vehicle.getStatus() != VehicleStatus.AVAILABLE) {
        	throw new BusinessRuleViolationException(
        	        "Vehicle is not available");
        }

        // Driver Availability
        if (driver.getStatus() != DriverStatus.AVAILABLE) {
        	throw new BusinessRuleViolationException(
        	        "Driver is not available");
        }

        // Fleet Validation
        if (!vehicle.getFleet().getId().equals(driver.getFleet().getId())) {
        	throw new BusinessRuleViolationException(
        	        "Driver and Vehicle must belong to the same Fleet");
        }
        
        shipment.setShipmentNumber(request.getShipmentNumber());
        shipment.setSource(request.getSource());
        shipment.setDestination(request.getDestination());
        shipment.setCargoDescription(request.getCargoDescription());
        shipment.setWeight(request.getWeight());

        shipment.setVehicle(vehicle);
        shipment.setDriver(driver);

        Shipment updatedShipment = shipmentRepository.save(shipment);

        return shipmentMapper.toResponse(updatedShipment);
	}

	@Override
	public void deleteShipment(Long id) {
		Shipment shipment = shipmentRepository.findById(id)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Shipment not found with id " + id));
	    shipmentRepository.delete(shipment);
	}

	@Override
	@Transactional
	public ShipmentResponseDTO assignShipment(Long shipmentId) {

	    Shipment shipment = shipmentRepository.findById(shipmentId)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Shipment not found with id " + shipmentId));

	    if (shipment.getStatus() != ShipmentStatus.CREATED) {
	        throw new BusinessRuleViolationException(
	                "Only CREATED shipments can be assigned.");
	    }

	    Driver driver = shipment.getDriver();
	    Vehicle vehicle = shipment.getVehicle();

	    if (driver.getStatus() != DriverStatus.AVAILABLE) {
	        throw new BusinessRuleViolationException(
	                "Driver is not available.");
	    }

	    if (vehicle.getStatus() != VehicleStatus.AVAILABLE) {
	        throw new BusinessRuleViolationException(
	                "Vehicle is not available.");
	    }

	    // Update driver status
	    driver.setStatus(DriverStatus.ON_TRIP);

	    // Update vehicle status
	    vehicle.setStatus(VehicleStatus.IN_TRANSIT);

	    // Update shipment status
	    shipment.setStatus(ShipmentStatus.ASSIGNED);

	    // Save changes
	    driverRepository.save(driver);
	    vehicleRepository.save(vehicle);

	    Shipment updatedShipment =
	            shipmentRepository.save(shipment);

	    // Create Kafka event
	    ShipmentEvent event = new ShipmentEvent(
	            ShipmentEventType.SHIPMENT_ASSIGNED,
	            updatedShipment.getId(),
	            updatedShipment.getShipmentNumber(),
	            vehicle.getId(),
	            driver.getId(),
	            LocalDateTime.now()
	    );

	    // Publish event to Kafka
	    shipmentEventProducer.publishShipmentEvent(event);

	    return shipmentMapper.toResponse(updatedShipment);
	}

	@Override
	@Transactional
	public ShipmentResponseDTO dispatchShipment(Long shipmentId) {
		Shipment shipment = shipmentRepository.findById(shipmentId)
				.orElseThrow(() ->
				  new ResourceNotFoundException(
						  "Shipment not found with id " + shipmentId));
		if (shipment.getStatus() != ShipmentStatus.ASSIGNED) {
			throw new BusinessRuleViolationException(
			        "Only ASSIGNED shipments can be dispatched.");
	    }
		shipment.setStatus(ShipmentStatus.IN_TRANSIT);
	    shipment.setDispatchDate(LocalDateTime.now());

	    Shipment updatedShipment = shipmentRepository.save(shipment);
	    // Create Kafka event
	    ShipmentEvent event = new ShipmentEvent(
	            ShipmentEventType.SHIPMENT_DISPATCHED,
	            updatedShipment.getId(),
	            updatedShipment.getShipmentNumber(),
	            updatedShipment.getVehicle().getId(),
	            updatedShipment.getDriver().getId(),
	            LocalDateTime.now()
	    );

	    // Publish event
	    shipmentEventProducer.publishShipmentEvent(event);

	    return shipmentMapper.toResponse(updatedShipment);
	}

	@Override
	@Transactional
	public ShipmentResponseDTO completeShipment(Long shipmentId) {
		Shipment shipment = shipmentRepository.findById(shipmentId)
				.orElseThrow(() ->
				  new ResourceNotFoundException(
						  "Shipment not found with id " + shipmentId));
		if(shipment.getStatus() != ShipmentStatus.IN_TRANSIT) {
			throw new BusinessRuleViolationException(
			        "Only IN_TRANSIT shipments can be completed.");
		}
		
		  Driver driver = shipment.getDriver();
		    Vehicle vehicle = shipment.getVehicle();

		    shipment.setStatus(ShipmentStatus.DELIVERED);
		    shipment.setDeliveryDate(LocalDateTime.now());

		    driver.setStatus(DriverStatus.AVAILABLE);

		    vehicle.setStatus(VehicleStatus.AVAILABLE);

		    driverRepository.save(driver);
		    vehicleRepository.save(vehicle);

		    Shipment updatedShipment = shipmentRepository.save(shipment);
		    // Create Kafka event
		    ShipmentEvent event = new ShipmentEvent(
		            ShipmentEventType.SHIPMENT_COMPLETED,
		            updatedShipment.getId(),
		            updatedShipment.getShipmentNumber(),
		            vehicle.getId(),
		            driver.getId(),
		            LocalDateTime.now()
		    );

		    // Publish event
		    shipmentEventProducer.publishShipmentEvent(event);

		    return shipmentMapper.toResponse(updatedShipment);
	}

	@Override
	@Transactional
	public ShipmentResponseDTO cancelShipment(Long shipmentId) {
		Shipment shipment = shipmentRepository.findById(shipmentId)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Shipment not found with id " + shipmentId));
		 if (shipment.getStatus() == ShipmentStatus.IN_TRANSIT ||
			        shipment.getStatus() == ShipmentStatus.DELIVERED ||
			        shipment.getStatus() == ShipmentStatus.CANCELLED) {

			 throw new BusinessRuleViolationException(
				        "Shipment cannot be cancelled.");
			    }
		 

		        Driver driver = shipment.getDriver();
		        Vehicle vehicle = shipment.getVehicle();
		 if (shipment.getStatus() == ShipmentStatus.ASSIGNED) {
		        driver.setStatus(DriverStatus.AVAILABLE);
		        vehicle.setStatus(VehicleStatus.AVAILABLE);

		        driverRepository.save(driver);
		        vehicleRepository.save(vehicle);
		    }
		 shipment.setStatus(ShipmentStatus.CANCELLED);

		    Shipment updatedShipment = shipmentRepository.save(shipment);
		    // Create Kafka event
		    ShipmentEvent event = new ShipmentEvent(
		            ShipmentEventType.SHIPMENT_CANCELLED,
		            updatedShipment.getId(),
		            updatedShipment.getShipmentNumber(),
		            vehicle != null ? vehicle.getId() : null,
		            driver != null ? driver.getId() : null,
		            LocalDateTime.now()
		    );

		    // Publish event
		    shipmentEventProducer.publishShipmentEvent(event);

		    return shipmentMapper.toResponse(updatedShipment);
	}
	

}
