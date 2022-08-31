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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SoftwareVersionedService {


    private final SoftwareVersionedRepository softwareVersionedRepository;

    private final SoftwareRepository softwareRepository;
    private final SoftwareVersionedMapper softwareVersionedMapper;

    public List<SoftwareVersionedResponseDTO> findAll() {
        log.info("Getting all software versioned");
        return softwareVersionedMapper.softwareVersionedListToSoftwareVersionedResponses(softwareVersionedRepository.findAll());
    }

    public SoftwareVersionedResponseDTO updateSoftwareVersioned(UUID id, SoftwareVersionedPatchRequestDTO softwareVersionedPatchRequestDTO) {
        log.info("Updating software versioned with id {}", id);
        SoftwareVersioned softwareVersioned = softwareVersionedRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Server not found"));


        softwareVersionedMapper.updateSoftwareVersioned(softwareVersionedPatchRequestDTO, softwareVersioned);


        return softwareVersionedMapper.softwareVersionedToSoftwareVersionedResponse(softwareVersionedRepository.save(softwareVersioned));

    }



    public SoftwareVersionedResponseDTO createSoftwareVersioned(SoftwareVersionedPostRequestDTO softwareVersionedPostRequestDTO) {
        log.info("Creating software versioned");

        SoftwareVersioned softwareVersioned = softwareVersionedMapper.softwareVersionedPostRequestDTOToSoftwareVersioned(softwareVersionedPostRequestDTO);
        Software software = softwareVersioned.getSoftware();


        softwareVersioned.setSoftware(softwareRepository.findById(software.getId()).orElse(softwareRepository.save(software)));
        return softwareVersionedMapper.softwareVersionedToSoftwareVersionedResponse(softwareVersionedRepository.save(softwareVersioned));
    }


    public void deleteSoftwareVersioned(UUID id) {
        log.info("Deleting software versioned with id {}", id);
        softwareVersionedRepository.deleteById(id);
    }

    public List<SoftwareVersionedResponseDTO> getAllSoftwareVersionedByServerId(UUID serverId) {
        log.info("Getting all software versioned by server id {}", serverId);
        return softwareVersionedMapper.softwareVersionedListToSoftwareVersionedResponses(softwareVersionedRepository.findAllByServer_id(serverId));
    }
}