package com.chizu.tsuru.api.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CreateWorkspaceDTO {

    @NotNull
    private String name;
    @NotNull
    private double minLat;
    @NotNull
    private double minLong;
    @NotNull
    private double maxLat;
    @NotNull
    private double maxLong;
    @NotNull
    private List<CreateLocationDTO> locations;

    public boolean isValid() {
        return minLat < maxLat && minLong < maxLong;
    }
}
