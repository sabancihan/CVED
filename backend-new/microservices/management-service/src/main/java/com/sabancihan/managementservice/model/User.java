package com.sabancihan.managementservice.model;


import lombok.*;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;



@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class User {

    @Id
    String username;


    @OneToMany(mappedBy = "user")
    Set<Server> servers;




}
