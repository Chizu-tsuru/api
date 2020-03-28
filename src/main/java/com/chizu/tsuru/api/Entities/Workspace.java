package com.chizu.tsuru.api.Entities;

import com.chizu.tsuru.api.DTO.GetWorkspaceDTO;
import com.chizu.tsuru.api.services.URIService;
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
@Table(name = "workspaces")
public class Workspace implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer workspace_id;

    @NotNull
    private String name;

    @JsonManagedReference
    @OneToMany(mappedBy = "workspace", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cluster> clusters;

    public GetWorkspaceDTO toResponse() {
        return GetWorkspaceDTO.builder()
                .name(name)
                .clusters(URIService.getClusters(workspace_id).toString())
                .build();
    }

}
