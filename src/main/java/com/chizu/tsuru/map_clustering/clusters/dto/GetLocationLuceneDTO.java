package com.chizu.tsuru.map_clustering.clusters.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetLocationLuceneDTO {
    private Integer locationId;
    private Double longitude;
    private Double latitude;
    private List<String> tags;
    private String cluster;
}
