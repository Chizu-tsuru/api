package com.chizu.tsuru.api.DTO;

import lombok.Data;

import java.util.List;

@Data
public class TravelDTO {

    private List<GetLocationDTO> locations;
    private double distance;
}
