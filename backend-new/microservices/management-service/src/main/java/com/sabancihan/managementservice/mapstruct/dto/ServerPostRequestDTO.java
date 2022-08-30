package com.sabancihan.managementservice.mapstruct.dto;

import com.sabancihan.managementservice.model.SoftwareVersioned;
import com.sabancihan.managementservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.http.conn.util.InetAddressUtils;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ServerPostRequestDTO {


    @NotNull
    private String ipAddress;

    @NotNull
    private Integer port;

    @NotNull
    private String user;


    private Set<SoftwareVersioned> software;


    private Set<HardwarePostRequestDTO> hardware;





    @AssertTrue
    public boolean isValidHost() {
        return InetAddressUtils.isIPv4Address(ipAddress) && port > 0 && port < 65536;
    }
}
