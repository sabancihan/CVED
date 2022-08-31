package com.sabancihan.managementservice.mapstruct.dto;


import com.sabancihan.managementservice.model.SoftwareVersioned;
import com.sabancihan.managementservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ServerResponseDTO {

    private UUID id;


    private String ipAddress;
    private Integer port;


    //Hardware




    private String cpu;
    private String ram;
    private String disk;

    //



    private Set<SoftwareVersionedResponseDTO> software;





}
