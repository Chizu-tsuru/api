package com.chizu.tsuru.api.clusters.controllers;

import com.chizu.tsuru.api.clusters.dto.GetLocationDTO;
import com.chizu.tsuru.api.clusters.services.LocationService;
import com.chizu.tsuru.api.shared.services.ResponseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;
    private final ResponseService responseService;

    public LocationController(LocationService locationService,
                              ResponseService responseService) {
        this.locationService = locationService;
        this.responseService = responseService;
    }

    @GetMapping("/{idLocation}")
    public GetLocationDTO getLocation(@PathVariable("idLocation") Integer idLocation) {
        return this.responseService
                .getLocationDTO(this.locationService.getLocation(idLocation));
    }
}
