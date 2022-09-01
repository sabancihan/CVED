package com.sabancihan.managementservice.service;

import com.sabancihan.managementservice.mapstruct.dto.*;
import com.sabancihan.managementservice.mapstruct.mapper.ServerMapper;
import com.sabancihan.managementservice.mapstruct.mapper.UserMapper;
import com.sabancihan.managementservice.model.Server;
import com.sabancihan.managementservice.model.Software;
import com.sabancihan.managementservice.model.SoftwareVersioned;
import com.sabancihan.managementservice.repository.ServerRepository;
import com.sabancihan.managementservice.repository.SoftwareRepository;
import com.sabancihan.managementservice.repository.SoftwareVersionedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional

public class ServerService {

    private final ServerRepository serverRepository;

    private final SoftwareRepository softwareRepository;

    private final UserMapper userMapper;

    private final SoftwareVersionedRepository softwareVersionedRepository;

    private final ServerMapper serverMapper;

    private final StreamBridge streamBridge;


    public List<ServerResponseDTO> getAllServersByUsername(String username) {
        log.info("Getting all servers by username: {}", username);
        return  serverMapper.serversToServerResponses(serverRepository.findAllByUser_Username(username));
    }

    public ServerResponseDTO getServerById(UUID id) {
        log.info("Getting server with id {}", id);
        return serverMapper.serverToServerResponse(serverRepository.findById(id).orElseThrow(() ->  new RuntimeException("Server not found")));
    }

    public List<ServerResponseDTO> getAllServers() {
        log.info("Getting all servers");
        return serverMapper.serversToServerResponses(serverRepository.findAll());
    }

    public ServerResponseDTO updateServer(UUID id, ServerPatchRequestDTO serverPatchRequestDTO) {
        log.info("Updating server with id {}", id);
        Server server = serverRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Server not found"));

        serverMapper.updateServer(serverPatchRequestDTO, server);



        return serverMapper.serverToServerResponse(serverRepository.save(server));

    }

    public void deleteServer(UUID id) {
        log.info("Deleting server with id {}", id);

        serverRepository.deleteById(id);
    }

    public ServerResponseDTO createServer(ServerPostRequestDTO serverPostRequestDTO) {
        log.info("Creating server");
        Server server = serverRepository.save(serverMapper.serverPostRequestDTOToServer(serverPostRequestDTO));


        Set<SoftwareVersioned> softwareSet = server.getSoftware();



        if (softwareSet != null) {
            softwareSet = server.getSoftware().stream().map(softwareVersioned -> {

                        Software software = softwareVersioned.getSoftware();

                        if (softwareRepository.findById(software.getId()).isEmpty())
                            softwareRepository.save(software);


                        softwareVersioned.setServer(server);



                        return softwareVersionedRepository.save(softwareVersioned);



                    }
            ).collect(Collectors.toSet());
            server.setSoftware(softwareSet);




            ManagementCollectionMessageDTO message =  ManagementCollectionMessageDTO.builder()
                    .user(userMapper.userToUserResponse(server.getUser()))
                    .server(serverMapper.serverToServerSummary(server))
                    .softwareVersioned(softwareSet.stream().map(
                            softwareVersioned -> SoftwareVersionedGetRequestDTO.builder()
                                    .version(softwareVersioned.getVersion())
                                    .software(SoftwareGetRequestDTO.builder()
                                            .id(softwareVersioned.getSoftware().getId())
                                            .build())
                                    .build()
                    ).collect(Collectors.toList()))

                    .build();

            log.info("Sending multiple software to management collection queue");


            streamBridge.send("managementUpdateEventSupplier-out-0", MessageBuilder.withPayload(message).build());

        }





        return serverMapper.serverToServerResponse(serverRepository.save(server));
    }
}
