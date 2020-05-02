package com.chizu.tsuru.api.clusters.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TravelDTO {

    private List<GetLocationDTO> locations;
    private double distance;
}
