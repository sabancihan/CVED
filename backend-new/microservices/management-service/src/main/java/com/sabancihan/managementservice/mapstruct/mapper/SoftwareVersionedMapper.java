package com.sabancihan.managementservice.mapstruct.mapper;

import com.sabancihan.managementservice.mapstruct.dto.*;
import com.sabancihan.managementservice.model.Server;
import com.sabancihan.managementservice.model.Software;
import com.sabancihan.managementservice.model.SoftwareId;
import com.sabancihan.managementservice.model.SoftwareVersioned;
import com.sabancihan.managementservice.service.ServerService;
import com.sabancihan.managementservice.service.SoftwareService;
import org.mapstruct.*;

import java.util.List;

@Mapper(uses = {JsonNullableMapper.class, SoftwareId.class, SoftwareService.class, ServerService.class},componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SoftwareVersionedMapper {


    SoftwareVersionedResponseDTO softwareVersionedToSoftwareVersionedResponse(SoftwareVersioned softwareVersioned);


    SoftwareVersioned softwareVersionedPatchRequestDTOToSoftwareVersioned(SoftwareVersionedPatchRequestDTO softwareVersionedPatchRequestDTO);

    SoftwareVersioned softwareVersionedPostRequestDTOToSoftwareVersioned(SoftwareVersionedPostRequestDTO softwareVersionedPostRequestDTO);

    SoftwareVersionedGetRequestDTO softwareVersionedToSoftwareVersionedGetRequest(SoftwareVersioned softwareVersioned);


    List<SoftwareVersionedResponseDTO> softwareVersionedListToSoftwareVersionedResponses(List<SoftwareVersioned> softwareVersionedList);




    @Mapping(target = "softwareName", qualifiedByName = "getSoftwareName", source = "software")
    @Mapping(target = "vendorName", qualifiedByName = "getVendorName", source = "software")
    ManagementVulnerabilityDTO softwareVersionedToManagementVulnerabilityDTO(SoftwareVersioned softwareVersioned);

    List<ManagementVulnerabilityDTO> softwareVersionedListToManagementVulnerabilityDTOList(List<SoftwareVersioned> softwareVersionedList);

    SoftwareVersioned softwareIdToSoftwareVersioned(SoftwareId softwareId);


    @Named("getSoftwareName")
    default String getSoftwareName(Software software) {
        return software.getId().getProduct_name();
    }

    @Named("getVendorName")
    default String getVendorName(Software software) {
        return software.getId().getVendor_name();
    }

    @InheritConfiguration(name = "softwareVersionedPatchRequestDTOToSoftwareVersioned")
    void updateSoftwareVersioned(SoftwareVersionedPatchRequestDTO softwareVersionedPatchRequestDTO, @MappingTarget SoftwareVersioned softwareVersioned);
}
