package com.sabancihan.managementservice.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Software {

    @Id
    @GeneratedValue
    private UUID id;



    String vendor_name;

    String product_name;


    @OneToMany(mappedBy = "software")
    Set<SoftwareVersioned> softwareVersions;




}