package com.sabancihan.managementservice.model;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class SoftwareVersioned {

    @Id
    @GeneratedValue
    private UUID id;


    @ManyToOne
    @JoinColumn(name = "server_id")
    private Server server;




    @ManyToOne
    @JoinColumn(name = "software_id")
    Software software;

    String version;



}
