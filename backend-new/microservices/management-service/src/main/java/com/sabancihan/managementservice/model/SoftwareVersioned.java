package com.sabancihan.managementservice.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Cacheable

@NamedEntityGraph(name = "SoftwareVersioned.withDetailedServer", attributeNodes =
        @NamedAttributeNode(value = "server", subgraph = "SoftwareVersioned.WithDetailedServer.Server"),
        subgraphs = @NamedSubgraph(name = "SoftwareVersioned.WithDetailedServer.Server", attributeNodes = {
                @NamedAttributeNode("ipAddress"),
                @NamedAttributeNode("port"),
                @NamedAttributeNode("user")
        })
)

@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SoftwareVersioned {

    public static final String VersionPattern = "(?!\\.)(\\d+(\\.\\d+)+)(?:[-.][A-Z]+)?(?![\\d.])$";


    @Id
    @GeneratedValue
    private UUID id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "server_id",nullable = false)
    @ToString.Exclude
    private Server server;




    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "software_vendor",nullable = false)
    @JoinColumn(name = "software_name",nullable = false)
    private Software software;


    @Column(nullable = false)
    private String version;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SoftwareVersioned that = (SoftwareVersioned) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
