package com.fleetops.dispatch.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fleetops.dispatch.dto.DispatchRequestDTO;
import com.fleetops.dispatch.dto.DispatchResponseDTO;

public interface DispatchService {

    DispatchResponseDTO createDispatch(DispatchRequestDTO request);

    Page<DispatchResponseDTO> getAllDispatches(Pageable pageable);

    DispatchResponseDTO getDispatchById(Long id);

    DispatchResponseDTO startDispatch(Long id);

    DispatchResponseDTO completeDispatch(Long id);

    DispatchResponseDTO cancelDispatch(Long id);
}