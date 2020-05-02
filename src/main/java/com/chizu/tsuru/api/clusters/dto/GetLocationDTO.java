package com.chizu.tsuru.api.clusters.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetLocationDTO {
    private Double longitude;
    private Double latitude;
    private String tags;
}
