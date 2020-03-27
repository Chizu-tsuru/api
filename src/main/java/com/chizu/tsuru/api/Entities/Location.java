package com.chizu.tsuru.api.Entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer locationId;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "cluster_id")
    private Cluster cluster;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "location_tag",
            joinColumns = @JoinColumn(name = "location_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

}
