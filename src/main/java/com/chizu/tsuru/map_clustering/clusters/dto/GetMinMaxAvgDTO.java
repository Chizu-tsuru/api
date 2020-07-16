package com.chizu.tsuru.map_clustering.clusters.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetMinMaxAvgDTO {
    private Integer numberLocations;
    private Double minMaxAvgDistance;
}
