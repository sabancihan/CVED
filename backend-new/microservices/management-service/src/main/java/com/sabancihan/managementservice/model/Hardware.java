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
public class Hardware {

    @Id
    @GeneratedValue
    private UUID id;

    String cpu;
    String ram;
    String disk;

    @ManyToOne
    @JoinColumn(name = "server_id")
    private Server server;




}
