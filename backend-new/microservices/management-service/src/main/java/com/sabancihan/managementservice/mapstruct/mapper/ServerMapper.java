package com.sabancihan.managementservice.mapstruct.mapper;



import com.sabancihan.managementservice.mapstruct.Qualifiers;
import com.sabancihan.managementservice.mapstruct.dto.ServerPatchRequestDTO;
import com.sabancihan.managementservice.mapstruct.dto.ServerPostRequestDTO;
import com.sabancihan.managementservice.mapstruct.dto.ServerResponseDTO;
import com.sabancihan.managementservice.mapstruct.dto.ServerSummaryDTO;
import com.sabancihan.managementservice.model.Server;
import com.sabancihan.managementservice.repository.ServerRepository;
import com.sabancihan.managementservice.service.ServerService;
import com.sabancihan.managementservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.*;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Mapper(uses = {JsonNullableMapper.class, UserService.class, ServerRepository.class,UserMapper.class},componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ServerMapper {



    ServerResponseDTO serverToServerResponse(Server server);

    Server serverPatchRequestDTOToServer(ServerPatchRequestDTO serverPatchRequestDTO);

    Server serverResponseDTOToServer(ServerResponseDTO serverResponseDTO);

    ServerSummaryDTO serverToServerSummary(Server server);



    Server serverPostRequestDTOToServer(ServerPostRequestDTO serverPostRequestDTO);


    List<ServerResponseDTO> serversToServerResponses(List<Server> servers);




    Server serverFromId(UUID id);

    @InheritConfiguration(name = "serverPatchRequestDTOToServer")
    void updateServer(ServerPatchRequestDTO serverPatchRequestDTO, @MappingTarget Server server);
}
