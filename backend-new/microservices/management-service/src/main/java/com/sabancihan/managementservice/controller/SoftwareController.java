package com.sabancihan.managementservice.controller;


import com.sabancihan.managementservice.mapstruct.dto.SoftwarePostRequestDTO;
import com.sabancihan.managementservice.mapstruct.dto.SoftwareResponseDTO;
import com.sabancihan.managementservice.model.Software;
import com.sabancihan.managementservice.service.SoftwareService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/management/software")
public class SoftwareController {
    private final SoftwareService softwareService;

     @GetMapping
     public List<SoftwareResponseDTO> getAllSoftware() {
         return softwareService.findAll();
     }

     @PostMapping
     public SoftwareResponseDTO createSoftware(SoftwarePostRequestDTO softwarePostRequestDTO) {
         return softwareService.createSoftware(softwarePostRequestDTO);
     }


}
