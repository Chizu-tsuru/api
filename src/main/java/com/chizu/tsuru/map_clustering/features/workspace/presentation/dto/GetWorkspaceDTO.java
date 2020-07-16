package com.chizu.tsuru.map_clustering.features.workspace.presentation.dto;

public class GetWorkspaceDTO {
    public String name;
    public String clusters;

    public GetWorkspaceDTO(String name, String clusters) {
        this.name = name;
        this.clusters = clusters;
    }
}