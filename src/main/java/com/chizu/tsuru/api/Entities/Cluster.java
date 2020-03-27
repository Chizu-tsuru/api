package com.chizu.tsuru.api.Entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "clusters")
public class Cluster implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer clusterId;

    @NotNull
    private double longitude;
    @NotNull
    private double latitude;

    private String area;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    @OneToMany(mappedBy = "cluster", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Location> locations;
}
