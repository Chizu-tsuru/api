package com.chizu.tsuru.api.core.services;

import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Data
@Service
public class URIService {

    public URI fromParent(Integer id) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }

    public URI getWorkspace(Integer id) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("workspaces/{id}")
                .buildAndExpand(id)
                .toUri();
    }

    public URI getClusters(Integer id) {
        return ServletUriComponentsBuilder
                .fromUri(getWorkspace(id))
                .path("/clusters")
                .build()
                .toUri();
    }

    public URI getCluster(Integer clusterId) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/clusters/{id}")
                .buildAndExpand(clusterId)
                .toUri();
    }

    public URI getLocations(Integer clusterId) {
        return ServletUriComponentsBuilder
                .fromUri(getCluster(clusterId))
                .path("/locations")
                .build()
                .toUri();
    }

    public URI getAddress(Integer addressId) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/addresses/{id}")
                .buildAndExpand(addressId)
                .toUri();
    }
}
