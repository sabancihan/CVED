package com.sabancihan.managementservice.mapstruct.dto;

import com.sabancihan.managementservice.model.SoftwareVersioned;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor

public class ServerSoftwarePostRequestDTO {

    @NotNull
    private SoftwarePostRequestDTO software;

    @NotNull
    private String version;


    @AssertTrue
    private boolean isVersionValid() {
        return version.matches(SoftwareVersioned.VersionPattern);
    }
}