package com.sabancihan.managementservice.service;

import com.sabancihan.managementservice.mapstruct.dto.*;
import com.sabancihan.managementservice.mapstruct.mapper.SoftwareVersionedMapper;
import com.sabancihan.managementservice.model.Server;
import com.sabancihan.managementservice.model.Software;
import com.sabancihan.managementservice.model.SoftwareId;
import com.sabancihan.managementservice.model.SoftwareVersioned;
import com.sabancihan.managementservice.repository.SoftwareRepository;
import com.sabancihan.managementservice.repository.SoftwareVersionedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SoftwareVersionedService {


    private final SoftwareVersionedRepository softwareVersionedRepository;
    private final SoftwareVersionedMapper softwareVersionedMapper;

    public List<SoftwareVersionedResponseDTO> findAll() {
        return softwareVersionedMapper.softwareVersionedListToSoftwareVersionedResponses(softwareVersionedRepository.findAll());
    }

    public SoftwareVersionedResponseDTO updateSoftwareVersioned(UUID id, SoftwareVersionedPatchRequestDTO softwareVersionedPatchRequestDTO) {
        SoftwareVersioned softwareVersioned = softwareVersionedRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Server not found"));


        softwareVersionedMapper.updateSoftwareVersioned(softwareVersionedPatchRequestDTO, softwareVersioned);


        return softwareVersionedMapper.softwareVersionedToSoftwareVersionedResponse(softwareVersionedRepository.save(softwareVersioned));

    }



    public SoftwareVersionedResponseDTO createSoftwareVersioned(SoftwareVersionedPostRequestDTO softwareVersionedPostRequestDTO) {

        SoftwareVersioned softwareVersioned = softwareVersionedMapper.softwareVersionedPostRequestDTOToSoftwareVersioned(softwareVersionedPostRequestDTO);
        return softwareVersionedMapper.softwareVersionedToSoftwareVersionedResponse(softwareVersionedRepository.save(softwareVersioned));
    }


    public void deleteSoftwareVersioned(UUID id) {
        softwareVersionedRepository.deleteById(id);
    }

    public List<SoftwareVersionedResponseDTO> getAllSoftwareVersionedByServerId(UUID serverId) {
        return softwareVersionedMapper.softwareVersionedListToSoftwareVersionedResponses(softwareVersionedRepository.findAllByServer_id(serverId));
    }
}