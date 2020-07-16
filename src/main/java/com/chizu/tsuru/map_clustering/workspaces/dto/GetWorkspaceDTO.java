package com.chizu.tsuru.map_clustering.workspaces.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetWorkspaceDTO {
    public String name;
    public String clusters;
}
