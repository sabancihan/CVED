package com.sabancihan.detectionservice.dto;

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
public class DetectionRequestDTO {

    @NotNull
    private String email;

    @NotNull
    private String ipAddress;

    @NotNull
    List<DetectionVulnerabilityDTO> vulnerabilities;


}
