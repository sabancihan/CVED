package com.sabancihan.managementservice.mapstruct.dto;


import lombok.*;

import java.util.List;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
public class ManagementCollectionMessageDTO  {



    UserResponseDTO user;
    ServerSummaryDTO server;
    List<SoftwareVersionedGetRequestDTO> softwareVersioned;






}
