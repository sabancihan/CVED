package com.sabancihan.managementservice.controller;

import com.sabancihan.managementservice.mapstruct.dto.*;
import com.sabancihan.managementservice.model.SoftwareId;
import com.sabancihan.managementservice.service.ServerService;
import com.sabancihan.managementservice.service.SoftwareVersionedService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/management/softwareVersioned")
public class SoftwareVersionedController {

    private final SoftwareVersionedService softwareVersionedService;

    @GetMapping
    public List<SoftwareVersionedResponseDTO> getAllServers() {

        return softwareVersionedService.findAll();
    }

    @PostMapping
    public SoftwareVersionedResponseDTO createSoftwareVersioned(@Valid  @RequestBody SoftwareVersionedPostRequestDTO softwareVersionedPostRequestDTO) {
        return softwareVersionedService.createSoftwareVersioned(softwareVersionedPostRequestDTO);
    }

    @GetMapping("{serverId}")
    public List<SoftwareVersionedResponseDTO> getAllSoftwareVersionedByServerId(@PathVariable UUID serverId) {
        return softwareVersionedService.getAllSoftwareVersionedByServerId(serverId);
    }



    @DeleteMapping("{id}")
    public void deleteSoftwareVersioned(@PathVariable UUID id) {
        softwareVersionedService.deleteSoftwareVersioned(id);
    }

    @PatchMapping("{id}")
    public SoftwareVersionedResponseDTO updateServer(@PathVariable UUID id, @Valid @RequestBody SoftwareVersionedPatchRequestDTO softwareVersionedPatchRequestDTO) {
        return softwareVersionedService.updateSoftwareVersioned(id, softwareVersionedPatchRequestDTO);
    }

    @PostMapping("/software/ids")
    public List<ManagementUpdateDTO> getAllSoftwareVersionedBySoftwareIds(@RequestBody Set<SoftwareId> softwareIds) {
        return softwareVersionedService.getAllSoftwareVersionedBySoftwareIds(softwareIds);
    }

    @GetMapping("/grouped")
    public List<ManagementUpdateDTO> getAllSoftwareGrouped() {
        return softwareVersionedService.getAllGrouped();
    }



}