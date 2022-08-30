package com.sabancihan.managementservice.mapstruct.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class SoftwareVersionedPatchRequestDTO {

    private String version;


}
