package com.chizu.tsuru.api.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CreateLocationDTO {

    @NotNull
    private double latitude;
    @NotNull
    private double longitude;
    private List<String> tags;
}
