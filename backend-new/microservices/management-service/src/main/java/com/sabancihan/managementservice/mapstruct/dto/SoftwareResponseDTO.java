package com.sabancihan.managementservice.mapstruct.dto;

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

    private UUID id;


    private String vendor_name;

    private String product_name;


}
