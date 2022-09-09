package com.sabancihan.managementservice.mapstruct.mapper;

import com.sabancihan.managementservice.mapstruct.dto.SoftwarePostRequestDTO;
import com.sabancihan.managementservice.mapstruct.dto.SoftwareResponseDTO;
import com.sabancihan.managementservice.model.Software;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-09T20:02:09+0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 18.0.2.1 (Oracle Corporation)"
)
@Component
public class SoftwareMapperImpl implements SoftwareMapper {

    @Override
    public SoftwareResponseDTO softwareToSoftwareResponse(Software software) {
        if ( software == null ) {
            return null;
        }

        SoftwareResponseDTO.SoftwareResponseDTOBuilder softwareResponseDTO = SoftwareResponseDTO.builder();

        softwareResponseDTO.id( software.getId() );

        return softwareResponseDTO.build();
    }

    @Override
    public Software softwarePostRequestDTOToSoftware(SoftwarePostRequestDTO softwarePostRequestDTO) {
        if ( softwarePostRequestDTO == null ) {
            return null;
        }

        Software.SoftwareBuilder software = Software.builder();

        software.id( softwarePostRequestDTO.getId() );

        return software.build();
    }

    @Override
    public List<SoftwareResponseDTO> softwareListToSoftwareResponses(List<Software> softwareList) {
        if ( softwareList == null ) {
            return null;
        }

        List<SoftwareResponseDTO> list = new ArrayList<SoftwareResponseDTO>( softwareList.size() );
        for ( Software software : softwareList ) {
            list.add( softwareToSoftwareResponse( software ) );
        }

        return list;
    }
}
