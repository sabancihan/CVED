package com.sabancihan.managementservice.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Cacheable


@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Server {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String ipAddress;
    @Column(nullable = false)
    private Integer port;

    @OneToMany(mappedBy = "server",fetch = FetchType.EAGER,orphanRemoval = true)

    private Set<SoftwareVersioned> software;


    //Hardware




    @Column(nullable = false)
    private String cpu;

    @Column(nullable = false)
    private String ram;

    @Column(nullable = false)
    private String disk;

    //


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_username", nullable = false)
    private  User user;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Server server = (Server) o;
        return id != null && Objects.equals(id, server.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
