package com.chizu.tsuru.api.workspaces.entities;

import com.chizu.tsuru.api.clusters.entities.Cluster;
import com.chizu.tsuru.api.workspaces.dto.GetWorkspaceDTO;
import com.chizu.tsuru.api.shared.services.URIService;
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
@Table(name = "workspaces")
public class Workspace implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer workspace_id;

    @NotNull
    private String name;

    @ToString.Exclude @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "workspace", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cluster> clusters;

}
