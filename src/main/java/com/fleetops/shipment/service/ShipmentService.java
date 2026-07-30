package com.fleetops.shipment.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fleetops.shipment.dto.ShipmentRequestDTO;
import com.fleetops.shipment.dto.ShipmentResponseDTO;

public interface ShipmentService {
	ShipmentResponseDTO createShipment(ShipmentRequestDTO request);

    Page<ShipmentResponseDTO> getAllShipments(Pageable pageable);

    ShipmentResponseDTO getShipmentById(Long id);

    ShipmentResponseDTO updateShipment(Long id,
                                       ShipmentRequestDTO request);
    
    void deleteShipment(Long id);
    ShipmentResponseDTO assignShipment(Long shipmentId);

    ShipmentResponseDTO dispatchShipment(Long shipmentId);

    ShipmentResponseDTO completeShipment(Long shipmentId);

    ShipmentResponseDTO cancelShipment(Long shipmentId);
}
