package com.chizu.tsuru.api.clusters.dto;

import lombok.Data;

@Data
public class GetClusterDTO {

    private Integer longitude;
    private Integer latitude;
    private String area;
    private String locations;
    private String workspace;
}