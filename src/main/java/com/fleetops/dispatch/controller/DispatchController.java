package com.fleetops.dispatch.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.fleetops.dispatch.dto.DispatchRequestDTO;
import com.fleetops.dispatch.dto.DispatchResponseDTO;
import com.fleetops.dispatch.service.DispatchService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/dispatches")
@RequiredArgsConstructor
@Validated
public class DispatchController {

    private final DispatchService dispatchService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DispatchResponseDTO createDispatch(
            @Valid @RequestBody DispatchRequestDTO request) {

        return dispatchService.createDispatch(request);
    }

    @GetMapping
    public Page<DispatchResponseDTO> getAllDispatches(
            Pageable pageable) {

        return dispatchService.getAllDispatches(pageable);
    }

    @GetMapping("/{id}")
    public DispatchResponseDTO getDispatchById(
            @PathVariable Long id) {

        return dispatchService.getDispatchById(id);
    }

    @PostMapping("/{id}/start")
    public DispatchResponseDTO startDispatch(
            @PathVariable Long id) {

        return dispatchService.startDispatch(id);
    }

    @PostMapping("/{id}/complete")
    public DispatchResponseDTO completeDispatch(
            @PathVariable Long id) {

        return dispatchService.completeDispatch(id);
    }

    @PostMapping("/{id}/cancel")
    public DispatchResponseDTO cancelDispatch(
            @PathVariable Long id) {

        return dispatchService.cancelDispatch(id);
    }
}