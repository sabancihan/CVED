package com.sabancihan.managementservice.mapstruct.dto;


import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagementUpdateDTO {

    @NotNull
    private String ipAddress;

    @NotNull
    private String email;

    @NotNull
    List<ManagementVulnerabilityDTO> vulnerabilities;


}

