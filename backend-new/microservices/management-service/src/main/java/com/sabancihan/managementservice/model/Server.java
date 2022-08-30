package com.sabancihan.managementservice.model;

import lombok.*;
import org.apache.http.conn.util.InetAddressUtils;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import java.util.Set;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Server {
    @Id
    @GeneratedValue
    private UUID id;

    String ipAddress;
    Integer port;

    @OneToMany(mappedBy = "server")

    Set<SoftwareVersioned> software;


    @OneToMany(mappedBy = "server")

    Set<Hardware> hardware;

    @ManyToOne
    @JoinColumn(name = "user_username")
    User user;



    @AssertTrue
    public boolean isValidHost() {
        return InetAddressUtils.isIPv4Address(ipAddress) && port > 0 && port < 65536;
    }



}
