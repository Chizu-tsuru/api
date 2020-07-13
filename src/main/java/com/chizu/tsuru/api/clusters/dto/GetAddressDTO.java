package com.chizu.tsuru.api.clusters.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAddressDTO {
    private Integer addressId;
    private String administrative_area_1;
    private String administrative_area_2;
    private String area;
    private String city;
    private String country;
}
