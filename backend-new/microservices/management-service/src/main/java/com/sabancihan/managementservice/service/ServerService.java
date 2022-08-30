package com.sabancihan.managementservice.service;

import com.sabancihan.managementservice.mapstruct.dto.ServerPatchRequestDTO;
import com.sabancihan.managementservice.mapstruct.dto.ServerPostRequestDTO;
import com.sabancihan.managementservice.mapstruct.dto.ServerResponseDTO;
import com.sabancihan.managementservice.mapstruct.mapper.ServerMapper;
import com.sabancihan.managementservice.model.Server;
import com.sabancihan.managementservice.repository.ServerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class ServerService {

    private final ServerRepository serverRepository;

    private final ServerMapper serverMapper;

    public List<ServerResponseDTO> getAllServersByUsername(String username) {
        return  serverMapper.serversToServerResponses(serverRepository.findAllByUser_Username(username));
    }

    public List<ServerResponseDTO> getAllServers() {
        return serverMapper.serversToServerResponses(serverRepository.findAll());
    }

    public ServerResponseDTO updateServer(UUID id, ServerPatchRequestDTO serverPatchRequestDTO) {
        Server server = serverRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Server not found"));

        serverMapper.updateServer(serverPatchRequestDTO, server);



        return serverMapper.serverToServerResponse(serverRepository.save(server));

    }

    public void deleteServer(UUID id) {
        serverRepository.deleteById(id);
    }

    public ServerResponseDTO createServer(ServerPostRequestDTO serverPostRequestDTO) {
        Server server = serverMapper.serverPostRequestDTOToServer(serverPostRequestDTO);
        return serverMapper.serverToServerResponse(serverRepository.save(server));
    }
}
