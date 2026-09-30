package com.fleetops.dispatch.mapper;

import org.springframework.stereotype.Component;

import com.fleetops.dispatch.dto.DispatchRequestDTO;
import com.fleetops.dispatch.dto.DispatchResponseDTO;
import com.fleetops.dispatch.entity.Dispatch;
import com.fleetops.shipment.entity.Shipment;

@Component
public class DispatchMapper {

    public Dispatch toEntity(
            DispatchRequestDTO request,
            Shipment shipment) {

        Dispatch dispatch = new Dispatch();

        dispatch.setShipment(shipment);
        dispatch.setDispatcherName(request.getDispatcherName());
        dispatch.setExpectedDelivery(request.getExpectedDelivery());
        dispatch.setRemarks(request.getRemarks());

        return dispatch;
    }

    public DispatchResponseDTO toResponse(Dispatch dispatch) {

        DispatchResponseDTO response = new DispatchResponseDTO();

        response.setId(dispatch.getId());

        if (dispatch.getShipment() != null) {
            response.setShipmentId(dispatch.getShipment().getId());
            response.setShipmentNumber(
                dispatch.getShipment().getShipmentNumber()
            );
        }

        response.setDispatcherName(dispatch.getDispatcherName());
        response.setDispatchTime(dispatch.getDispatchTime());
        response.setExpectedDelivery(dispatch.getExpectedDelivery());
        response.setActualDelivery(dispatch.getActualDelivery());
        response.setStatus(dispatch.getStatus());
        response.setRemarks(dispatch.getRemarks());
        response.setCreatedAt(dispatch.getCreatedAt());

        return response;
    }
}