package com.sabancihan.managementservice.service;

import com.sabancihan.managementservice.mapstruct.dto.ServerPatchRequestDTO;
import com.sabancihan.managementservice.mapstruct.dto.ServerPostRequestDTO;
import com.sabancihan.managementservice.mapstruct.dto.ServerResponseDTO;
import com.sabancihan.managementservice.mapstruct.mapper.ServerMapper;
import com.sabancihan.managementservice.model.Server;
import com.sabancihan.managementservice.model.Software;
import com.sabancihan.managementservice.model.SoftwareVersioned;
import com.sabancihan.managementservice.repository.ServerRepository;
import com.sabancihan.managementservice.repository.SoftwareRepository;
import com.sabancihan.managementservice.repository.SoftwareVersionedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional

public class ServerService {

    private final ServerRepository serverRepository;

    private final SoftwareRepository softwareRepository;

    private final SoftwareVersionedRepository softwareVersionedRepository;

    private final ServerMapper serverMapper;

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
            server.setSoftware(server.getSoftware().stream().map(softwareVersioned -> {

                Software software = softwareVersioned.getSoftware();

                        if (softwareRepository.findById(software.getId()).isEmpty())
                            softwareRepository.save(software);


                        softwareVersioned.setServer(server);

                        return softwareVersionedRepository.save(softwareVersioned);



                    }
            ).collect(Collectors.toSet()));

        }



        return serverMapper.serverToServerResponse(serverRepository.save(server));
    }
}
