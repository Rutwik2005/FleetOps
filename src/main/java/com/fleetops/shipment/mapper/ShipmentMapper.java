package com.fleetops.shipment.mapper;

import org.springframework.stereotype.Component;

import com.fleetops.driver.entity.Driver;
import com.fleetops.shipment.dto.ShipmentRequestDTO;
import com.fleetops.shipment.dto.ShipmentResponseDTO;
import com.fleetops.shipment.entity.Shipment;
import com.fleetops.vehicle.entity.Vehicle;
@Component
public class ShipmentMapper {
     public Shipment toEntity(ShipmentRequestDTO request,Vehicle vehicle,Driver driver) {
		Shipment shipment = new Shipment();
		shipment.setShipmentNumber(request.getShipmentNumber());
		shipment.setSource(request.getSource());
		shipment.setDestination(request.getDestination());
		shipment.setCargoDescription(request.getCargoDescription());
		shipment.setWeight(request.getWeight());
		
		shipment.setVehicle(vehicle);
		shipment.setDriver(driver);
		return shipment;
     }
     
     public ShipmentResponseDTO toResponse(Shipment shipment) {

         ShipmentResponseDTO response = new ShipmentResponseDTO();

         response.setId(shipment.getId());
         response.setShipmentNumber(shipment.getShipmentNumber());
         response.setSource(shipment.getSource());
         response.setDestination(shipment.getDestination());
         response.setCargoDescription(shipment.getCargoDescription());
         response.setWeight(shipment.getWeight());

         response.setStatus(shipment.getStatus());
         response.setDispatchDate(shipment.getDispatchDate());
         response.setDeliveryDate(shipment.getDeliveryDate());
         response.setCreatedAt(shipment.getCreatedAt());

         response.setVehicleId(shipment.getVehicle().getId());
         response.setVehicleRegistrationNumber(
                 shipment.getVehicle().getRegistrationNumber());

         response.setDriverId(shipment.getDriver().getId());
         response.setDriverName(
                 shipment.getDriver().getFirstName() + " "
                 + shipment.getDriver().getLastName());

         return response;
     }
}
