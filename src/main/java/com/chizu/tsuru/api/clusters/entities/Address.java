package com.chizu.tsuru.api.clusters.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "address")
public class Address implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer addressId;

    private String area;

    private String administrativeAreaOne;

    private String administrativeAreaTwo;

    private String country;

    private String city;

    @ToString.Exclude
    @OneToOne(mappedBy = "address", cascade = CascadeType.ALL)
    private Cluster cluster;

}
