package com.fleetops.shipment.controller;

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

import com.fleetops.shipment.dto.ShipmentRequestDTO;
import com.fleetops.shipment.dto.ShipmentResponseDTO;
import com.fleetops.shipment.service.ShipmentService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/shipments")
@RequiredArgsConstructor
@Validated
public class ShipmentController {
   private final ShipmentService shipmentService;
   
   @PostMapping
   @ResponseStatus(HttpStatus.CREATED)
   public ShipmentResponseDTO createShipment(@Valid @RequestBody ShipmentRequestDTO request) {
	   return shipmentService.createShipment(request);
   }
   
   @GetMapping
   public Page<ShipmentResponseDTO> getAllShipments(Pageable pageable) {
	   return shipmentService.getAllShipments(pageable);
   }
   
   @GetMapping("/{id}")
   public ShipmentResponseDTO getShipmentById(@PathVariable Long id) {
	   return shipmentService.getShipmentById(id);
   }
   
   @PutMapping("/{id}")
   public ShipmentResponseDTO updateShipment(
           @PathVariable Long id,
           @Valid @RequestBody ShipmentRequestDTO request) {

       return shipmentService.updateShipment(id, request);
   }

   @DeleteMapping("/{id}")
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void deleteShipment(@PathVariable Long id) {

       shipmentService.deleteShipment(id);
   }
   
   @PostMapping("/{id}/assign")
   public ShipmentResponseDTO assignShipment(@PathVariable Long id) {

       return shipmentService.assignShipment(id);
   }
   
   @PostMapping("/{id}/dispatch")
   public ShipmentResponseDTO dispatchShipment(@PathVariable Long id) {

       return shipmentService.dispatchShipment(id);
   }
   
   @PostMapping("/{id}/complete")
   public ShipmentResponseDTO completeShipment(@PathVariable Long id) {

       return shipmentService.completeShipment(id);
   }
   
   @PostMapping("/{id}/cancel")
   public ShipmentResponseDTO cancelShipment(@PathVariable Long id) {
	   return shipmentService.cancelShipment(id);
   }
   
}
