package com.chizu.tsuru.api.Entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "workspaces")
public class Workspace implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer workspace_id;

    @NotNull
    private String name;

    @OneToMany(mappedBy = "workspace", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cluster> clusters;
}
