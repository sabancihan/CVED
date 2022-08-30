package com.sabancihan.managementservice.mapstruct.dto;


import com.sabancihan.managementservice.model.Hardware;
import com.sabancihan.managementservice.model.SoftwareVersioned;
import com.sabancihan.managementservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ServerResponseDTO {


    private String ipAddress;
    private Integer port;

    private List<HardwareResponseDTO> hardware;



    private Set<SoftwareVersioned> software;


    private User user;



}
