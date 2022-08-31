package com.sabancihan.managementservice.mapstruct.dto;

import com.sabancihan.managementservice.model.Server;
import com.sabancihan.managementservice.model.Software;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class SoftwareVersionedResponseDTO {

    private UUID id;

    private SoftwareResponseDTO software;

    private String version;
}
