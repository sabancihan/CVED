package com.sabancihan.managementservice.mapstruct.mapper;

import com.sabancihan.managementservice.mapstruct.dto.SoftwarePostRequestDTO;
import com.sabancihan.managementservice.mapstruct.dto.SoftwareResponseDTO;
import com.sabancihan.managementservice.mapstruct.dto.SoftwareVersionedPatchRequestDTO;
import com.sabancihan.managementservice.mapstruct.dto.SoftwareVersionedPostRequestDTO;
import com.sabancihan.managementservice.mapstruct.dto.SoftwareVersionedResponseDTO;
import com.sabancihan.managementservice.model.Software;
import com.sabancihan.managementservice.model.SoftwareVersioned;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-31T17:03:50+0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 18.0.2.1 (Oracle Corporation)"
)
@Component
public class SoftwareVersionedMapperImpl implements SoftwareVersionedMapper {

    @Autowired
    private ServerMapper serverMapper;

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

        softwareVersioned.server( serverMapper.serverFromId( softwareVersionedPostRequestDTO.getServer() ) );
        softwareVersioned.software( softwarePostRequestDTOToSoftware( softwareVersionedPostRequestDTO.getSoftware() ) );
        softwareVersioned.version( softwareVersionedPostRequestDTO.getVersion() );

        return softwareVersioned.build();
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

    protected Software softwarePostRequestDTOToSoftware(SoftwarePostRequestDTO softwarePostRequestDTO) {
        if ( softwarePostRequestDTO == null ) {
            return null;
        }

        Software.SoftwareBuilder software = Software.builder();

        software.id( softwarePostRequestDTO.getId() );

        return software.build();
    }
}
