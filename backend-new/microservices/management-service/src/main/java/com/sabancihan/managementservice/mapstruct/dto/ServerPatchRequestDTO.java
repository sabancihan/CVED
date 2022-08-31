package com.sabancihan.managementservice.mapstruct.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.http.conn.util.InetAddressUtils;
import org.openapitools.jackson.nullable.JsonNullable;

import javax.validation.constraints.AssertTrue;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ServerPatchRequestDTO {

    private JsonNullable<String> ipAddress;
    private JsonNullable<Integer> port;

    private JsonNullable<String> userUsername;

    //Hardware




    private JsonNullable<String> cpu;
    private JsonNullable<String> ram;
    private JsonNullable<String> disk;

    //

    @AssertTrue
    public boolean isValidIpAddress() {
        return ipAddress.isPresent() ||  InetAddressUtils.isIPv4Address(ipAddress.get());
    }


    @AssertTrue
    public boolean isValidPort() {
        return port.isPresent() ||  (port.get() > 0 && port.get() < 65536);
    }



}
