package com.sabancihan.managementservice.service;

import com.google.common.collect.Sets;
import com.sabancihan.managementservice.mapstruct.dto.*;
import com.sabancihan.managementservice.mapstruct.mapper.ServerMapper;
import com.sabancihan.managementservice.mapstruct.mapper.SoftwareVersionedMapper;
import com.sabancihan.managementservice.mapstruct.mapper.UserMapper;
import com.sabancihan.managementservice.model.Server;
import com.sabancihan.managementservice.model.Software;
import com.sabancihan.managementservice.model.SoftwareId;
import com.sabancihan.managementservice.model.SoftwareVersioned;
import com.sabancihan.managementservice.repository.ServerRepository;
import com.sabancihan.managementservice.repository.SoftwareRepository;
import com.sabancihan.managementservice.repository.SoftwareVersionedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SoftwareVersionedService {

    private final StreamBridge streamBridge;

    private final UserMapper userMapper;

    private final ServerMapper serverMapper;

    private final SoftwareVersionedRepository softwareVersionedRepository;

    private final ServerRepository serverRepository;

    private final SoftwareRepository softwareRepository;
    private final SoftwareVersionedMapper softwareVersionedMapper;

    public List<SoftwareVersionedResponseDTO> findAll() {
        log.info("Getting all software versioned");
        return softwareVersionedMapper.softwareVersionedListToSoftwareVersionedResponses(softwareVersionedRepository.findAll());
    }

    public SoftwareVersionedResponseDTO updateSoftwareVersioned(UUID id, SoftwareVersionedPatchRequestDTO softwareVersionedPatchRequestDTO) {
        log.info("Updating software versioned with id {}", id);
        SoftwareVersioned softwareVersioned = softwareVersionedRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Server not found"));


        softwareVersionedMapper.updateSoftwareVersioned(softwareVersionedPatchRequestDTO, softwareVersioned);


        return softwareVersionedMapper.softwareVersionedToSoftwareVersionedResponse(softwareVersionedRepository.save(softwareVersioned));

    }



    public SoftwareVersionedResponseDTO createSoftwareVersioned(SoftwareVersionedPostRequestDTO softwareVersionedPostRequestDTO) {


        log.info("Creating software versioned");

        SoftwareId softwareId = softwareVersionedPostRequestDTO.getSoftware();

        Software software = softwareRepository.findById(softwareId).orElse(softwareRepository.save(Software.builder().id(softwareId).softwareVersions(new HashSet<>()).build()));

        Server server = serverRepository.findById(softwareVersionedPostRequestDTO.getServer()).orElseThrow(() -> new IllegalArgumentException("Server not found"));




        SoftwareVersioned  softwareVersioned = SoftwareVersioned.builder()
                .id(UUID.randomUUID())
                .version(softwareVersionedPostRequestDTO.getVersion())
                .software(software)
                .server(server)
                .build();


        SoftwareVersioned savedSoftwareVersioned = softwareVersionedRepository.save(softwareVersioned);

        software.getSoftwareVersions().addAll(Sets.newHashSet(savedSoftwareVersioned));

        var message = ManagementUpdateDTO.builder()
                .email(server.getUser().getEmail())
                .ipAddress(server.getIpAddress())
                .vulnerabilities(Collections.singletonList(ManagementVulnerabilityDTO.builder()
                                .softwareName(software.getId().getProduct_name())
                                .vendorName(software.getId().getVendor_name())
                                .version(softwareVersioned.getVersion())
                        .build()))
                .build();


        log.info("Sending single software to management collection queue");


        streamBridge.send("managementUpdateEventSupplier-out-0", MessageBuilder.withPayload(message).build());



        return softwareVersionedMapper.softwareVersionedToSoftwareVersionedResponse(softwareVersioned);
    }


    public void deleteSoftwareVersioned(UUID id) {
        log.info("Deleting software versioned with id {}", id);
        softwareVersionedRepository.deleteById(id);
    }

    public List<SoftwareVersionedResponseDTO> getAllSoftwareVersionedByServerId(UUID serverId) {
        log.info("Getting all software versioned by server id {}", serverId);
        return softwareVersionedMapper.softwareVersionedListToSoftwareVersionedResponses(softwareVersionedRepository.findAllByServer_id(serverId));
    }

    public List<ManagementUpdateDTO> getAllGrouped() {
        log.info("Getting all grouped software versioned");
        return groupListByServer(softwareVersionedRepository.findAll());
    }



    private List<ManagementUpdateDTO> groupListByServer(List<SoftwareVersioned> softwareVersionedList) {
        if (softwareVersionedList.isEmpty()) {
            return null;
        }

        var groupServer =  softwareVersionedList.stream().collect(Collectors.groupingBy(
                SoftwareVersioned::getServer
        ));



        return  groupServer.keySet().stream().map(
                server -> ManagementUpdateDTO.builder()
                        .email(server.getUser().getEmail())
                        .ipAddress(server.getIpAddress())
                        .vulnerabilities(groupServer.get(server).stream().map(
                                softwareVersionedMapper::softwareVersionedToManagementVulnerabilityDTO
                        ).toList())
                        .build()
        ).collect(Collectors.toList());
    }

    public List<ManagementUpdateDTO> getAllSoftwareVersionedBySoftwareIds (Set<SoftwareId> ids) {
        List<SoftwareVersioned> softwareVersionedList = softwareVersionedRepository.findAllBySoftware_idIn(ids);


        return groupListByServer(softwareVersionedList);


    }
}