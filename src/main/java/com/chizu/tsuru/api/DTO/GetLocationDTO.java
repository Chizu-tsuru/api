package com.chizu.tsuru.api.DTO;

import lombok.Data;

@Data
public class GetLocationDTO {
    private Double longitude;
    private Double latitude;
    private String tags;
}
