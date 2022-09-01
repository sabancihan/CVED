package com.sabancihan.managementservice.mapstruct.mapper;

import com.sabancihan.managementservice.mapstruct.dto.*;
import com.sabancihan.managementservice.model.Server;
import com.sabancihan.managementservice.model.Software;
import com.sabancihan.managementservice.model.SoftwareId;
import com.sabancihan.managementservice.model.SoftwareVersioned;
import com.sabancihan.managementservice.service.ServerService;
import com.sabancihan.managementservice.service.SoftwareService;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(uses = {JsonNullableMapper.class, SoftwareId.class, SoftwareService.class, ServerService.class,ServerMapper.class},componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SoftwareVersionedMapper {


    SoftwareVersionedResponseDTO softwareVersionedToSoftwareVersionedResponse(SoftwareVersioned softwareVersioned);


    SoftwareVersioned softwareVersionedPatchRequestDTOToSoftwareVersioned(SoftwareVersionedPatchRequestDTO softwareVersionedPatchRequestDTO);

    SoftwareVersioned softwareVersionedPostRequestDTOToSoftwareVersioned(SoftwareVersionedPostRequestDTO softwareVersionedPostRequestDTO);

    SoftwareVersionedGetRequestDTO softwareVersionedToSoftwareVersionedGetRequest(SoftwareVersioned softwareVersioned);


    List<SoftwareVersionedResponseDTO> softwareVersionedListToSoftwareVersionedResponses(List<SoftwareVersioned> softwareVersionedList);



    @InheritConfiguration(name = "softwareVersionedPatchRequestDTOToSoftwareVersioned")
    void updateSoftwareVersioned(SoftwareVersionedPatchRequestDTO softwareVersionedPatchRequestDTO, @MappingTarget SoftwareVersioned softwareVersioned);
}
