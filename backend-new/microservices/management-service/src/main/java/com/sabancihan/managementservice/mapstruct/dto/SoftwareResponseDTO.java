package com.sabancihan.managementservice.mapstruct.dto;

import com.sabancihan.managementservice.model.SoftwareId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor

public class SoftwareResponseDTO {



private SoftwareId id;


}
