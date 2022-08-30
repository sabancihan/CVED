package com.sabancihan.managementservice.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Software {



    @EmbeddedId
    private SoftwareId id;


    @OneToMany(mappedBy = "software")
    Set<SoftwareVersioned> softwareVersions;


    public Software(SoftwareId id) {
        this.id = id;
        this.softwareVersions = new HashSet<>();
    }
}