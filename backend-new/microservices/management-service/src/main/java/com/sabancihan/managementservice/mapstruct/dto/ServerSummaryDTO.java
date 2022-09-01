package com.sabancihan.managementservice.mapstruct.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ServerSummaryDTO {

        private String ipAddress;
        private Integer port;
        private UserResponseDTO user;

}
