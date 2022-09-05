package com.sabancihan.managementservice.mapstruct.mapper;

import com.sabancihan.managementservice.mapstruct.dto.ManagementVulnerabilityDTO;
import com.sabancihan.managementservice.mapstruct.dto.SoftwareGetRequestDTO;
import com.sabancihan.managementservice.mapstruct.dto.SoftwareResponseDTO;
import com.sabancihan.managementservice.mapstruct.dto.SoftwareVersionedGetRequestDTO;
import com.sabancihan.managementservice.mapstruct.dto.SoftwareVersionedPatchRequestDTO;
import com.sabancihan.managementservice.mapstruct.dto.SoftwareVersionedPostRequestDTO;
import com.sabancihan.managementservice.mapstruct.dto.SoftwareVersionedResponseDTO;
import com.sabancihan.managementservice.model.Software;
import com.sabancihan.managementservice.model.SoftwareId;
import com.sabancihan.managementservice.model.SoftwareVersioned;
import com.sabancihan.managementservice.service.ServerService;
import com.sabancihan.managementservice.service.SoftwareService;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-05T14:10:07+0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 18.0.2.1 (Oracle Corporation)"
)
@Component
public class SoftwareVersionedMapperImpl implements SoftwareVersionedMapper {

    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private ServerService serverService;

    @Override
    public SoftwareVersionedResponseDTO softwareVersionedToSoftwareVersionedResponse(SoftwareVersioned softwareVersioned) {
        if ( softwareVersioned == null ) {
            return null;
        }

        SoftwareVersionedResponseDTO.SoftwareVersionedResponseDTOBuilder softwareVersionedResponseDTO = SoftwareVersionedResponseDTO.builder();

        softwareVersionedResponseDTO.id( softwareVersioned.getId() );
        softwareVersionedResponseDTO.software( softwareToSoftwareResponseDTO( softwareVersioned.getSoftware() ) );
        softwareVersionedResponseDTO.version( softwareVersioned.getVersion() );

        return softwareVersionedResponseDTO.build();
    }

    @Override
    public SoftwareVersioned softwareVersionedPatchRequestDTOToSoftwareVersioned(SoftwareVersionedPatchRequestDTO softwareVersionedPatchRequestDTO) {
        if ( softwareVersionedPatchRequestDTO == null ) {
            return null;
        }

        SoftwareVersioned.SoftwareVersionedBuilder softwareVersioned = SoftwareVersioned.builder();

        softwareVersioned.version( softwareVersionedPatchRequestDTO.getVersion() );

        return softwareVersioned.build();
    }

    @Override
    public SoftwareVersioned softwareVersionedPostRequestDTOToSoftwareVersioned(SoftwareVersionedPostRequestDTO softwareVersionedPostRequestDTO) {
        if ( softwareVersionedPostRequestDTO == null ) {
            return null;
        }

        SoftwareVersioned.SoftwareVersionedBuilder softwareVersioned = SoftwareVersioned.builder();

        softwareVersioned.server( serverService.getServerById( softwareVersionedPostRequestDTO.getServer() ) );
        softwareVersioned.software( softwareService.findOrCreateSoftware( softwareVersionedPostRequestDTO.getSoftware() ) );
        softwareVersioned.version( softwareVersionedPostRequestDTO.getVersion() );

        return softwareVersioned.build();
    }

    @Override
    public SoftwareVersionedGetRequestDTO softwareVersionedToSoftwareVersionedGetRequest(SoftwareVersioned softwareVersioned) {
        if ( softwareVersioned == null ) {
            return null;
        }

        SoftwareVersionedGetRequestDTO.SoftwareVersionedGetRequestDTOBuilder softwareVersionedGetRequestDTO = SoftwareVersionedGetRequestDTO.builder();

        softwareVersionedGetRequestDTO.version( softwareVersioned.getVersion() );
        softwareVersionedGetRequestDTO.software( softwareToSoftwareGetRequestDTO( softwareVersioned.getSoftware() ) );

        return softwareVersionedGetRequestDTO.build();
    }

    @Override
    public List<SoftwareVersionedResponseDTO> softwareVersionedListToSoftwareVersionedResponses(List<SoftwareVersioned> softwareVersionedList) {
        if ( softwareVersionedList == null ) {
            return null;
        }

        List<SoftwareVersionedResponseDTO> list = new ArrayList<SoftwareVersionedResponseDTO>( softwareVersionedList.size() );
        for ( SoftwareVersioned softwareVersioned : softwareVersionedList ) {
            list.add( softwareVersionedToSoftwareVersionedResponse( softwareVersioned ) );
        }

        return list;
    }

    @Override
    public ManagementVulnerabilityDTO softwareVersionedToManagementVulnerabilityDTO(SoftwareVersioned softwareVersioned) {
        if ( softwareVersioned == null ) {
            return null;
        }

        ManagementVulnerabilityDTO.ManagementVulnerabilityDTOBuilder managementVulnerabilityDTO = ManagementVulnerabilityDTO.builder();

        managementVulnerabilityDTO.softwareName( getSoftwareName( softwareVersioned.getSoftware() ) );
        managementVulnerabilityDTO.vendorName( getVendorName( softwareVersioned.getSoftware() ) );
        managementVulnerabilityDTO.version( softwareVersioned.getVersion() );

        return managementVulnerabilityDTO.build();
    }

    @Override
    public List<ManagementVulnerabilityDTO> softwareVersionedListToManagementVulnerabilityDTOList(List<SoftwareVersioned> softwareVersionedList) {
        if ( softwareVersionedList == null ) {
            return null;
        }

        List<ManagementVulnerabilityDTO> list = new ArrayList<ManagementVulnerabilityDTO>( softwareVersionedList.size() );
        for ( SoftwareVersioned softwareVersioned : softwareVersionedList ) {
            list.add( softwareVersionedToManagementVulnerabilityDTO( softwareVersioned ) );
        }

        return list;
    }

    @Override
    public SoftwareVersioned softwareIdToSoftwareVersioned(SoftwareId softwareId) {
        if ( softwareId == null ) {
            return null;
        }

        SoftwareVersioned.SoftwareVersionedBuilder softwareVersioned = SoftwareVersioned.builder();

        return softwareVersioned.build();
    }

    @Override
    public void updateSoftwareVersioned(SoftwareVersionedPatchRequestDTO softwareVersionedPatchRequestDTO, SoftwareVersioned softwareVersioned) {
        if ( softwareVersionedPatchRequestDTO == null ) {
            return;
        }

        if ( softwareVersionedPatchRequestDTO.getVersion() != null ) {
            softwareVersioned.setVersion( softwareVersionedPatchRequestDTO.getVersion() );
        }
    }

    protected SoftwareResponseDTO softwareToSoftwareResponseDTO(Software software) {
        if ( software == null ) {
            return null;
        }

        SoftwareResponseDTO.SoftwareResponseDTOBuilder softwareResponseDTO = SoftwareResponseDTO.builder();

        softwareResponseDTO.id( software.getId() );

        return softwareResponseDTO.build();
    }

    protected SoftwareGetRequestDTO softwareToSoftwareGetRequestDTO(Software software) {
        if ( software == null ) {
            return null;
        }

        SoftwareGetRequestDTO.SoftwareGetRequestDTOBuilder softwareGetRequestDTO = SoftwareGetRequestDTO.builder();

        softwareGetRequestDTO.id( software.getId() );

        return softwareGetRequestDTO.build();
    }
}
