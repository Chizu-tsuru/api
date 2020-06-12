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
public class Address{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer addressId;

    private String area;

    private String administrative_area_1;

    private String administrative_area_2;

    private String country;

    private String city;

}
