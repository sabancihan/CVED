package com.sabancihan.managementservice.mapstruct.mapper;



import com.sabancihan.managementservice.mapstruct.dto.ServerPatchRequestDTO;
import com.sabancihan.managementservice.mapstruct.dto.ServerPostRequestDTO;
import com.sabancihan.managementservice.mapstruct.dto.ServerResponseDTO;
import com.sabancihan.managementservice.model.Server;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.openapitools.jackson.nullable.JsonNullable;

import java.util.List;

@Mapper(uses = {JsonNullableMapper.class},componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ServerMapper {


    ServerResponseDTO serverToServerResponse(Server server);

    Server serverPatchRequestDTOToServer(ServerPatchRequestDTO serverPatchRequestDTO);

    Server serverPostRequestDTOToServer(ServerPostRequestDTO serverPostRequestDTO);


    List<ServerResponseDTO> serversToServerResponses(List<Server> servers);


    @InheritConfiguration(name = "serverPatchRequestDTOToServer")
    void updateServer(ServerPatchRequestDTO serverPatchRequestDTO, @MappingTarget Server server);
}
