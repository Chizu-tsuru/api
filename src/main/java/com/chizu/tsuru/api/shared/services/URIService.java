package com.chizu.tsuru.api.shared.services;

import lombok.Data;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Data
public class URIService {

    public static URI fromParent(Integer id) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }

    public static URI getWorkspace(Integer id) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("workspaces/{id}")
                .buildAndExpand(id)
                .toUri();
    }

    public static URI getClusters(Integer id) {
        return ServletUriComponentsBuilder
                .fromUri(getWorkspace(id))
                .path("/clusters")
                .build()
                .toUri();
    }

    public static URI getCluster(Integer workspaceId, Integer clusterId) {
        return ServletUriComponentsBuilder
                .fromUri(getWorkspace(workspaceId))
                .path("/clusters/{id}")
                .buildAndExpand(clusterId)
                .toUri();
    }
}
