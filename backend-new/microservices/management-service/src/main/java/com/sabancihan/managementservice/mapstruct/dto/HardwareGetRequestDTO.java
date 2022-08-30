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
public class HardwareGetRequestDTO {


    private  String cpu;
    private  String ram;
    private  String disk;

}
