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
public class DetectionVulnerabiltiyDTO {

    @NotNull
    private String softwareName;

    @NotNull
    private String vendorName;

    @NotNull
    private List<SoftwareVulnerabilityDTO> affectedVersions;

    @NotNull
    private String usedVersion;

}
