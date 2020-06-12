package com.chizu.tsuru.api.clusters.dto;

import com.chizu.tsuru.api.clusters.entities.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetClusterDTO {

    private Double longitude;
    private Double latitude;
    private String area;
    private String locations;
    private String address;
    private String workspace;
}
