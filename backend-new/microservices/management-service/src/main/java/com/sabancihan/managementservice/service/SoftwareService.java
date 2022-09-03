package com.sabancihan.managementservice.service;


import com.sabancihan.managementservice.mapstruct.dto.SoftwarePostRequestDTO;
import com.sabancihan.managementservice.mapstruct.dto.SoftwareResponseDTO;
import com.sabancihan.managementservice.mapstruct.mapper.SoftwareMapper;
import com.sabancihan.managementservice.model.Server;
import com.sabancihan.managementservice.model.Software;
import com.sabancihan.managementservice.model.SoftwareId;
import com.sabancihan.managementservice.repository.SoftwareRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SoftwareService {
    private final SoftwareRepository softwareRepository;

    private final SoftwareMapper softwareMapper;

    public List<SoftwareResponseDTO> findAll() {
        log.info("Getting all software");
        return softwareMapper.softwareListToSoftwareResponses(softwareRepository.findAll());
    }

    public SoftwareResponseDTO createSoftware(SoftwarePostRequestDTO softwarePostRequestDTO) {
        log.info("Creating software");

        Software software = softwareMapper.softwarePostRequestDTOToSoftware(softwarePostRequestDTO);
        return softwareMapper.softwareToSoftwareResponse(softwareRepository.save(software));
    }

    public SoftwareResponseDTO findById(SoftwareId id) {
        log.info("Getting software with id {}", id);
        return softwareMapper.softwareToSoftwareResponse(softwareRepository.findById(id).orElse(new Software(id)));
    }


    public Software findOrCreateSoftware(SoftwareId id) {
        log.info("Getting software with id {}", id);
        return softwareRepository.findById(id).orElseGet(() -> softwareRepository.save(new Software(id)));
    }


}
