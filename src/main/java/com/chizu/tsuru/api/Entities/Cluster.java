package com.chizu.tsuru.api.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @JsonBackReference @ToString.Exclude @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL  )
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    @JsonManagedReference
    @OneToMany(mappedBy = "cluster", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Location> locations;
}
