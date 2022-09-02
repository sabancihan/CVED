package com.sabancihan.collectionservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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