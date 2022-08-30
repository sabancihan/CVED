package com.sabancihan.managementservice.mapstruct.dto;

import com.sabancihan.managementservice.model.Hardware;
import com.sabancihan.managementservice.model.SoftwareVersioned;
import com.sabancihan.managementservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.http.conn.util.InetAddressUtils;
import org.openapitools.jackson.nullable.JsonNullable;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.AssertTrue;
import java.util.Set;
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ServerPatchRequestDTO {

    private JsonNullable<String> ipAddress;
    private JsonNullable<Integer> port;

    private JsonNullable<String> userUsername;

    @AssertTrue
    public boolean isValidIpAddress() {
        return ipAddress.isPresent() ||  InetAddressUtils.isIPv4Address(ipAddress.get());
    }


    @AssertTrue
    public boolean isValidPort() {
        return port.isPresent() ||  (port.get() > 0 && port.get() < 65536);
    }



}
