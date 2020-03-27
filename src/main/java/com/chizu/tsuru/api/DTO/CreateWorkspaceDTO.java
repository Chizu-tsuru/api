package com.chizu.tsuru.api.DTO;

import com.chizu.tsuru.api.Entities.Location;

import javax.validation.constraints.NotNull;
import java.util.List;

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
    private List<Location> locations;
}
