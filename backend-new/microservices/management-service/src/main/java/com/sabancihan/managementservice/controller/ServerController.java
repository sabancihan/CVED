package com.sabancihan.managementservice.controller;

import com.sabancihan.managementservice.mapstruct.dto.ServerPatchRequestDTO;
import com.sabancihan.managementservice.mapstruct.dto.ServerPostRequestDTO;
import com.sabancihan.managementservice.mapstruct.dto.ServerResponseDTO;
import com.sabancihan.managementservice.service.ServerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/management/server")
public class ServerController {

    private final ServerService serverService;

    @GetMapping
    public List<ServerResponseDTO> getAllServers() {

        return serverService.getAllServers();
    }

    @PostMapping
    public ServerResponseDTO createServer(@RequestBody ServerPostRequestDTO serverPostRequestDTO) {
        return serverService.createServer(serverPostRequestDTO);
    }

    @GetMapping("{username}")
    public List<ServerResponseDTO> getAllServersByUsername(@PathVariable String username) {
        return serverService.getAllServersByUsername(username);
    }



    @DeleteMapping("{id}")
    public void deleteServer(@PathVariable UUID id) {
        serverService.deleteServer(id);
    }

    @PatchMapping("{id}")
    public ServerResponseDTO updateServer(@PathVariable UUID id, @RequestBody ServerPatchRequestDTO serverPatchRequestDTO) {
       return serverService.updateServer(id, serverPatchRequestDTO);
    }



}
