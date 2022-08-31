package com.sabancihan.managementservice.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter


@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Software {



    @EmbeddedId
    private SoftwareId id;


    @OneToMany(mappedBy = "software",fetch = FetchType.LAZY,orphanRemoval = true)
    @ToString.Exclude
    private Set<SoftwareVersioned> softwareVersions;


    public Software(SoftwareId id) {
        this.id = id;
        this.softwareVersions = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Software software = (Software) o;
        return id != null && Objects.equals(id, software.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}