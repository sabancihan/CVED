package com.sabancihan.managementservice.controller;

import com.sabancihan.managementservice.mapstruct.dto.ServerPatchRequestDTO;
import com.sabancihan.managementservice.mapstruct.dto.ServerPostRequestDTO;
import com.sabancihan.managementservice.mapstruct.dto.ServerResponseDTO;
import com.sabancihan.managementservice.service.ServerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/management/server")
public class ServerController {

    private final ServerService serverService;

    public final static String adminRoleEnglish = "ROLE_ADMINS";
    public final static String adminRoleTurkish = "ROLE_ADMİNS";

    @GetMapping
    public List<ServerResponseDTO> getAllServers() {

        return serverService.getAllServers();
    }

    @PostMapping
    public ServerResponseDTO createServer(@RequestHeader String user_email,@RequestHeader String user_id, @RequestBody ServerPostRequestDTO serverPostRequestDTO) {

        serverPostRequestDTO.setUser(user_id);
        return serverService.createServer(user_email,serverPostRequestDTO);

    }

    @GetMapping("user")
    public List<ServerResponseDTO> getAllServersByUser(@RequestHeader String user_id) {

        return serverService.getAllServersByUsername(user_id);
    }

    @GetMapping("user/{username}")
    public List<ServerResponseDTO> getAllServersByUsername(@PathVariable String username,@RequestHeader String user_id,@RequestHeader String user_role) {


        if (Objects.equals(user_id, username) || user_role.equals(adminRoleTurkish) || user_role.equals(adminRoleEnglish)) {
            return serverService.getAllServersByUsername(username);
        }
        return serverService.getAllServersByUsername(username);
    }

    @GetMapping("{id}")
    public ServerResponseDTO getServerById(@PathVariable UUID id) {
        return serverService.getServerResponseById(id);
    }



    @DeleteMapping("{id}")
    public void deleteServer(@PathVariable UUID id) {
        serverService.deleteServer(id);
    }

    @PatchMapping("{id}")
    public ServerResponseDTO updateServer(@RequestHeader String user_role,@PathVariable UUID id, @Valid @RequestBody ServerPatchRequestDTO serverPatchRequestDTO) {
        if (user_role.equals(adminRoleEnglish) || user_role.equals(adminRoleTurkish)) {
            return serverService.updateServer(id, serverPatchRequestDTO);
        } else {
            throw new RuntimeException("You are not authorized to update server");
        }
    }



}
