package com.chizu.tsuru.api.DTO;

import lombok.Data;

import java.util.List;

@Data
public class CreateLocationDTO {

    private double latitude;
    private double longitude;
    private List<CreateTagDTO> tags;
}
