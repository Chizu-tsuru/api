package com.chizu.tsuru.map_clustering.core.services;

import com.chizu.tsuru.map_clustering.clusters.dto.GetAddressDTO;
import com.chizu.tsuru.map_clustering.clusters.dto.GetClusterDTO;
import com.chizu.tsuru.map_clustering.clusters.dto.GetLocationDTO;
import com.chizu.tsuru.map_clustering.clusters.dto.GetLocationLuceneDTO;
import com.chizu.tsuru.map_clustering.clusters.entities.Address;
import com.chizu.tsuru.map_clustering.clusters.entities.Cluster;
import com.chizu.tsuru.map_clustering.clusters.entities.Location;
import com.chizu.tsuru.map_clustering.clusters.entities.Tag;
import com.chizu.tsuru.map_clustering.workspaces.dto.GetWorkspaceDTO;
import com.chizu.tsuru.map_clustering.workspaces.entities.Workspace;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResponseOldService {

    private final URIService uriService;

    public ResponseOldService(URIService uriService) {
        this.uriService = uriService;
    }

    public GetWorkspaceDTO getWorkspaceDTO(Workspace w) {
        return GetWorkspaceDTO.builder()
                .name(w.getName())
                .clusters(this.uriService.getClusters(w.getWorkspaceId()).toString())
                .build();
    }

    public GetClusterDTO getClusterDTO(Cluster c) {
        return GetClusterDTO.builder()
                .area(c.getArea())
                .latitude(c.getLatitude())
                .longitude(c.getLongitude())
                .address(this.uriService.getAddress(c.getAddress().getAddressId()).toString())
                .locations(this.uriService.getLocations(c.getClusterId()).toString())
                .workspace(this.uriService.getWorkspace(c.getWorkspace().getWorkspaceId()).toString())
                .build();
    }

    public GetLocationDTO getLocationDTO(Location l) {
        return GetLocationDTO.builder()
                .latitude(l.getLatitude())
                .longitude(l.getLongitude())
                .tags(l.getTags().stream().map(Tag::getName).collect(Collectors.toList()))
                .build();
    }

    public List<GetLocationLuceneDTO> getLocationLuceneDTO(List<GetLocationLuceneDTO> getLocationDTOList) {
        for (GetLocationLuceneDTO item : getLocationDTOList) {
            item.setCluster(this.uriService.getCluster(Integer.parseInt(item.getCluster())).toString());
        }
        return getLocationDTOList;
    }

    public GetAddressDTO getAddressDTO(Address a) {
        return GetAddressDTO.builder()
                .addressId(a.getAddressId())
                .administrative_area_1(a.getAdministrativeAreaOne())
                .administrative_area_2(a.getAdministrativeAreaTwo())
                .area(a.getArea())
                .city(a.getCity())
                .country(a.getCountry())
                .build();
    }
}