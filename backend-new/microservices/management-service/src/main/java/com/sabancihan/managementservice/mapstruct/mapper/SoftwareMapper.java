package com.sabancihan.managementservice.mapstruct.mapper;

import com.sabancihan.managementservice.mapstruct.dto.*;
import com.sabancihan.managementservice.model.Server;
import com.sabancihan.managementservice.model.Software;
import com.sabancihan.managementservice.model.SoftwareId;
import com.sabancihan.managementservice.service.SoftwareService;
import org.mapstruct.*;

import java.util.List;

@Mapper(uses = {JsonNullableMapper.class},componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SoftwareMapper {



    SoftwareResponseDTO softwareToSoftwareResponse(Software software);




    Software softwarePostRequestDTOToSoftware(SoftwarePostRequestDTO softwarePostRequestDTO);


    List<SoftwareResponseDTO> softwareListToSoftwareResponses(List<Software> softwareList);



}