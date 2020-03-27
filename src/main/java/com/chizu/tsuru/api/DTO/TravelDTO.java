package com.chizu.tsuru.api.DTO;

import com.chizu.tsuru.api.Entities.Cluster;
import lombok.Data;

@Data
public class TravelDTO {

    private Cluster cluster;
    private double distance;
}
