package com.sabancihan.managementservice.mapstruct.mapper;

import com.sabancihan.managementservice.mapstruct.dto.ServerPatchRequestDTO;
import com.sabancihan.managementservice.mapstruct.dto.ServerPostRequestDTO;
import com.sabancihan.managementservice.mapstruct.dto.ServerResponseDTO;
import com.sabancihan.managementservice.mapstruct.dto.ServerSoftwarePostRequestDTO;
import com.sabancihan.managementservice.mapstruct.dto.ServerSummaryDTO;
import com.sabancihan.managementservice.mapstruct.dto.SoftwarePostRequestDTO;
import com.sabancihan.managementservice.mapstruct.dto.SoftwareResponseDTO;
import com.sabancihan.managementservice.mapstruct.dto.SoftwareVersionedResponseDTO;
import com.sabancihan.managementservice.model.Server;
import com.sabancihan.managementservice.model.Software;
import com.sabancihan.managementservice.model.SoftwareVersioned;
import com.sabancihan.managementservice.service.UserService;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-05T14:10:07+0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 18.0.2.1 (Oracle Corporation)"
)
@Component
public class ServerMapperImpl implements ServerMapper {

    @Autowired
    private JsonNullableMapper jsonNullableMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponseDTO serverToServerResponse(Server server) {
        if ( server == null ) {
            return null;
        }

        ServerResponseDTO.ServerResponseDTOBuilder serverResponseDTO = ServerResponseDTO.builder();

        serverResponseDTO.id( server.getId() );
        serverResponseDTO.ipAddress( server.getIpAddress() );
        serverResponseDTO.port( server.getPort() );
        serverResponseDTO.cpu( server.getCpu() );
        serverResponseDTO.ram( server.getRam() );
        serverResponseDTO.disk( server.getDisk() );
        serverResponseDTO.software( softwareVersionedSetToSoftwareVersionedResponseDTOSet( server.getSoftware() ) );

        return serverResponseDTO.build();
    }

    @Override
    public Server serverPatchRequestDTOToServer(ServerPatchRequestDTO serverPatchRequestDTO) {
        if ( serverPatchRequestDTO == null ) {
            return null;
        }

        Server.ServerBuilder server = Server.builder();

        if ( jsonNullableMapper.isPresent( serverPatchRequestDTO.getIpAddress() ) ) {
            server.ipAddress( jsonNullableMapper.unwrap( serverPatchRequestDTO.getIpAddress() ) );
        }
        if ( jsonNullableMapper.isPresent( serverPatchRequestDTO.getPort() ) ) {
            server.port( jsonNullableMapper.unwrap( serverPatchRequestDTO.getPort() ) );
        }
        if ( jsonNullableMapper.isPresent( serverPatchRequestDTO.getCpu() ) ) {
            server.cpu( jsonNullableMapper.unwrap( serverPatchRequestDTO.getCpu() ) );
        }
        if ( jsonNullableMapper.isPresent( serverPatchRequestDTO.getRam() ) ) {
            server.ram( jsonNullableMapper.unwrap( serverPatchRequestDTO.getRam() ) );
        }
        if ( jsonNullableMapper.isPresent( serverPatchRequestDTO.getDisk() ) ) {
            server.disk( jsonNullableMapper.unwrap( serverPatchRequestDTO.getDisk() ) );
        }

        return server.build();
    }

    @Override
    public Server serverResponseDTOToServer(ServerResponseDTO serverResponseDTO) {
        if ( serverResponseDTO == null ) {
            return null;
        }

        Server.ServerBuilder server = Server.builder();

        server.id( serverResponseDTO.getId() );
        server.ipAddress( serverResponseDTO.getIpAddress() );
        server.port( serverResponseDTO.getPort() );
        server.software( softwareVersionedResponseDTOSetToSoftwareVersionedSet( serverResponseDTO.getSoftware() ) );
        server.cpu( serverResponseDTO.getCpu() );
        server.ram( serverResponseDTO.getRam() );
        server.disk( serverResponseDTO.getDisk() );

        return server.build();
    }

    @Override
    public ServerSummaryDTO serverToServerSummary(Server server) {
        if ( server == null ) {
            return null;
        }

        ServerSummaryDTO.ServerSummaryDTOBuilder serverSummaryDTO = ServerSummaryDTO.builder();

        serverSummaryDTO.ipAddress( server.getIpAddress() );
        serverSummaryDTO.port( server.getPort() );
        serverSummaryDTO.user( userMapper.userToUserResponse( server.getUser() ) );

        return serverSummaryDTO.build();
    }

    @Override
    public Server serverPostRequestDTOToServer(ServerPostRequestDTO serverPostRequestDTO) {
        if ( serverPostRequestDTO == null ) {
            return null;
        }

        Server.ServerBuilder server = Server.builder();

        server.ipAddress( serverPostRequestDTO.getIpAddress() );
        server.port( serverPostRequestDTO.getPort() );
        server.software( serverSoftwarePostRequestDTOSetToSoftwareVersionedSet( serverPostRequestDTO.getSoftware() ) );
        server.cpu( serverPostRequestDTO.getCpu() );
        server.ram( serverPostRequestDTO.getRam() );
        server.disk( serverPostRequestDTO.getDisk() );
        server.user( userService.getUserByUsername( serverPostRequestDTO.getUser() ) );

        return server.build();
    }

    @Override
    public List<ServerResponseDTO> serversToServerResponses(List<Server> servers) {
        if ( servers == null ) {
            return null;
        }

        List<ServerResponseDTO> list = new ArrayList<ServerResponseDTO>( servers.size() );
        for ( Server server : servers ) {
            list.add( serverToServerResponse( server ) );
        }

        return list;
    }

    @Override
    public Server serverFromId(UUID id) {
        if ( id == null ) {
            return null;
        }

        Server.ServerBuilder server = Server.builder();

        server.id( id );

        return server.build();
    }

    @Override
    public void updateServer(ServerPatchRequestDTO serverPatchRequestDTO, Server server) {
        if ( serverPatchRequestDTO == null ) {
            return;
        }

        if ( jsonNullableMapper.isPresent( serverPatchRequestDTO.getIpAddress() ) ) {
            server.setIpAddress( jsonNullableMapper.unwrap( serverPatchRequestDTO.getIpAddress() ) );
        }
        if ( jsonNullableMapper.isPresent( serverPatchRequestDTO.getPort() ) ) {
            server.setPort( jsonNullableMapper.unwrap( serverPatchRequestDTO.getPort() ) );
        }
        if ( jsonNullableMapper.isPresent( serverPatchRequestDTO.getCpu() ) ) {
            server.setCpu( jsonNullableMapper.unwrap( serverPatchRequestDTO.getCpu() ) );
        }
        if ( jsonNullableMapper.isPresent( serverPatchRequestDTO.getRam() ) ) {
            server.setRam( jsonNullableMapper.unwrap( serverPatchRequestDTO.getRam() ) );
        }
        if ( jsonNullableMapper.isPresent( serverPatchRequestDTO.getDisk() ) ) {
            server.setDisk( jsonNullableMapper.unwrap( serverPatchRequestDTO.getDisk() ) );
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

    protected SoftwareVersionedResponseDTO softwareVersionedToSoftwareVersionedResponseDTO(SoftwareVersioned softwareVersioned) {
        if ( softwareVersioned == null ) {
            return null;
        }

        SoftwareVersionedResponseDTO.SoftwareVersionedResponseDTOBuilder softwareVersionedResponseDTO = SoftwareVersionedResponseDTO.builder();

        softwareVersionedResponseDTO.id( softwareVersioned.getId() );
        softwareVersionedResponseDTO.software( softwareToSoftwareResponseDTO( softwareVersioned.getSoftware() ) );
        softwareVersionedResponseDTO.version( softwareVersioned.getVersion() );

        return softwareVersionedResponseDTO.build();
    }

    protected Set<SoftwareVersionedResponseDTO> softwareVersionedSetToSoftwareVersionedResponseDTOSet(Set<SoftwareVersioned> set) {
        if ( set == null ) {
            return null;
        }

        Set<SoftwareVersionedResponseDTO> set1 = new LinkedHashSet<SoftwareVersionedResponseDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( SoftwareVersioned softwareVersioned : set ) {
            set1.add( softwareVersionedToSoftwareVersionedResponseDTO( softwareVersioned ) );
        }

        return set1;
    }

    protected Software softwareResponseDTOToSoftware(SoftwareResponseDTO softwareResponseDTO) {
        if ( softwareResponseDTO == null ) {
            return null;
        }

        Software.SoftwareBuilder software = Software.builder();

        software.id( softwareResponseDTO.getId() );

        return software.build();
    }

    protected SoftwareVersioned softwareVersionedResponseDTOToSoftwareVersioned(SoftwareVersionedResponseDTO softwareVersionedResponseDTO) {
        if ( softwareVersionedResponseDTO == null ) {
            return null;
        }

        SoftwareVersioned.SoftwareVersionedBuilder softwareVersioned = SoftwareVersioned.builder();

        softwareVersioned.id( softwareVersionedResponseDTO.getId() );
        softwareVersioned.software( softwareResponseDTOToSoftware( softwareVersionedResponseDTO.getSoftware() ) );
        softwareVersioned.version( softwareVersionedResponseDTO.getVersion() );

        return softwareVersioned.build();
    }

    protected Set<SoftwareVersioned> softwareVersionedResponseDTOSetToSoftwareVersionedSet(Set<SoftwareVersionedResponseDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<SoftwareVersioned> set1 = new LinkedHashSet<SoftwareVersioned>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( SoftwareVersionedResponseDTO softwareVersionedResponseDTO : set ) {
            set1.add( softwareVersionedResponseDTOToSoftwareVersioned( softwareVersionedResponseDTO ) );
        }

        return set1;
    }

    protected Software softwarePostRequestDTOToSoftware(SoftwarePostRequestDTO softwarePostRequestDTO) {
        if ( softwarePostRequestDTO == null ) {
            return null;
        }

        Software.SoftwareBuilder software = Software.builder();

        software.id( softwarePostRequestDTO.getId() );

        return software.build();
    }

    protected SoftwareVersioned serverSoftwarePostRequestDTOToSoftwareVersioned(ServerSoftwarePostRequestDTO serverSoftwarePostRequestDTO) {
        if ( serverSoftwarePostRequestDTO == null ) {
            return null;
        }

        SoftwareVersioned.SoftwareVersionedBuilder softwareVersioned = SoftwareVersioned.builder();

        softwareVersioned.software( softwarePostRequestDTOToSoftware( serverSoftwarePostRequestDTO.getSoftware() ) );
        softwareVersioned.version( serverSoftwarePostRequestDTO.getVersion() );

        return softwareVersioned.build();
    }

    protected Set<SoftwareVersioned> serverSoftwarePostRequestDTOSetToSoftwareVersionedSet(Set<ServerSoftwarePostRequestDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<SoftwareVersioned> set1 = new LinkedHashSet<SoftwareVersioned>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( ServerSoftwarePostRequestDTO serverSoftwarePostRequestDTO : set ) {
            set1.add( serverSoftwarePostRequestDTOToSoftwareVersioned( serverSoftwarePostRequestDTO ) );
        }

        return set1;
    }
}
