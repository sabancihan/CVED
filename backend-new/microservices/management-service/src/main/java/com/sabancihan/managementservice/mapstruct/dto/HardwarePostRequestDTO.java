package com.sabancihan.managementservice.mapstruct.dto;

import com.sabancihan.managementservice.model.Server;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class HardwarePostRequestDTO {

    private String cpu;
    private String ram;
    private String disk;

    private String server;
}
