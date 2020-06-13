package com.chizu.tsuru.api.shared.services;

import com.chizu.tsuru.api.clusters.dto.GetAddressDTO;
import com.chizu.tsuru.api.clusters.dto.GetClusterDTO;
import com.chizu.tsuru.api.clusters.dto.GetLocationDTO;
import com.chizu.tsuru.api.clusters.dto.GetLocationLuceneDTO;
import com.chizu.tsuru.api.clusters.entities.Address;
import com.chizu.tsuru.api.clusters.entities.Cluster;
import com.chizu.tsuru.api.clusters.entities.Location;
import com.chizu.tsuru.api.clusters.entities.Tag;
import com.chizu.tsuru.api.workspaces.dto.GetWorkspaceDTO;
import com.chizu.tsuru.api.workspaces.entities.Workspace;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResponseService {

    private final URIService uriService;
    public ResponseService(URIService uriService) {
        this.uriService = uriService;
    }

    public GetWorkspaceDTO getWorkspaceDTO(Workspace w) {
        return GetWorkspaceDTO.builder()
                .name(w.getName())
                .clusters(this.uriService.getClusters(w.getWorkspace_id()).toString())
                .build();
    }

    public GetClusterDTO getClusterDTO(Cluster c) {
                return GetClusterDTO.builder()
                .area(c.getArea())
                .latitude(c.getLatitude())
                .longitude(c.getLongitude())
                .address(this.uriService.getAddress(c.getAddress().getAddressId()).toString())
                .locations(this.uriService.getLocations(c.getClusterId()).toString())
                .workspace(this.uriService.getWorkspace(c.getWorkspace().getWorkspace_id()).toString())
                .build();
    }

    public GetLocationDTO getLocationDTO(Location l) {
        return GetLocationDTO.builder()
                .latitude(l.getLatitude())
                .longitude(l.getLongitude())
                .tags(l.getTags().stream().map(Tag::getName).collect(Collectors.toList()))
                .build();
    }

    public List<GetLocationLuceneDTO> getLocationLuceneDTO(List<GetLocationLuceneDTO> getLocationDTOList){
        for(GetLocationLuceneDTO item : getLocationDTOList){
            System.out.println(item.getCluster());
            item.setCluster(this.uriService.getCluster(Integer.parseInt(item.getCluster())).toString());
        }
        return getLocationDTOList;
    }

    public GetAddressDTO getAddressDTO(Address a){
        return GetAddressDTO.builder()
                .addressId(a.getAddressId())
                .administrative_area_1(a.getAdministrative_area_1())
                .administrative_area_2(a.getAdministrative_area_2())
                .area(a.getArea())
                .city(a.getCity())
                .country(a.getCountry())
                .build();
    }

}
