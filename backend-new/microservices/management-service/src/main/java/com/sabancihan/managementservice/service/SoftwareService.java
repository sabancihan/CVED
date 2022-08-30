package com.sabancihan.managementservice.service;


import com.sabancihan.managementservice.mapstruct.dto.SoftwarePostRequestDTO;
import com.sabancihan.managementservice.mapstruct.dto.SoftwareResponseDTO;
import com.sabancihan.managementservice.mapstruct.mapper.SoftwareMapper;
import com.sabancihan.managementservice.model.Server;
import com.sabancihan.managementservice.model.Software;
import com.sabancihan.managementservice.model.SoftwareId;
import com.sabancihan.managementservice.repository.SoftwareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SoftwareService {
    private final SoftwareRepository softwareRepository;

    private final SoftwareMapper softwareMapper;

    public List<SoftwareResponseDTO> findAll() {
        return softwareMapper.softwareListToSoftwareResponses(softwareRepository.findAll());
    }

    public SoftwareResponseDTO createSoftware(SoftwarePostRequestDTO softwarePostRequestDTO) {

        Software software = softwareMapper.softwarePostRequestDTOToSoftware(softwarePostRequestDTO);
        return softwareMapper.softwareToSoftwareResponse(softwareRepository.save(software));
    }

    public SoftwareResponseDTO findById(SoftwareId id) {
        return softwareMapper.softwareToSoftwareResponse(softwareRepository.findById(id).orElse(new Software(id)));
    }




}
