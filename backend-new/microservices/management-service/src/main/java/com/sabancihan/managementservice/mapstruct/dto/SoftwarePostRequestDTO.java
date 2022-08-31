package com.sabancihan.managementservice.mapstruct.dto;

import com.sabancihan.managementservice.model.SoftwareId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class SoftwarePostRequestDTO {

    private SoftwareId id;
}
