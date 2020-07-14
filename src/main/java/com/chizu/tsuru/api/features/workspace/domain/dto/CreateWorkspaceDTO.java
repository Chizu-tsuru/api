package com.chizu.tsuru.api.features.workspace.domain.dto;

import com.chizu.tsuru.api.core.errors.BadRequestException;

import java.util.List;

public class CreateWorkspaceDTO {
    private final String name;
    private final double minLat;
    private final double minLong;
    private final double maxLat;
    private final double maxLong;
    private final List<CreateLocationDTO> locations;

    public CreateWorkspaceDTO(String name, double minLat, double minLong, double maxLat, double maxLong, List<CreateLocationDTO> locations) {

        if(minLat > maxLat) {
            throw new BadRequestException("limitation point are invalid");
        }

        if (name == null ||locations == null) {
            throw new BadRequestException("Name and locations can't be null");
        }
        this.name = name;
        this.minLat = minLat;
        this.minLong = minLong;
        this.maxLat = maxLat;
        this.maxLong = maxLong;
        this.locations = locations;
    }

    public String getName() {
        return name;
    }

    public double getMinLat() {
        return minLat;
    }

    public double getMinLong() {
        return minLong;
    }

    public double getMaxLat() {
        return maxLat;
    }

    public double getMaxLong() {
        return maxLong;
    }

    public List<CreateLocationDTO> getLocations() {
        return locations;
    }
}
