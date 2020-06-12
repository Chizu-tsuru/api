package com.chizu.tsuru.api.clusters.controllers;

import com.chizu.tsuru.api.clusters.dto.GetLocationDTO;
import com.chizu.tsuru.api.clusters.services.LocationService;
import com.chizu.tsuru.api.clusters.services.LuceneService;
import com.chizu.tsuru.api.config.Configuration;
import com.chizu.tsuru.api.shared.services.ResponseService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;
    private final ResponseService responseService;
    private final LuceneService luceneService;
    private final Configuration configuration;
    private final int default_count;

    public LocationController(LocationService locationService,
                              ResponseService responseService,
                              LuceneService luceneService,
                              Configuration configuration) {
        this.locationService = locationService;
        this.responseService = responseService;
        this.luceneService = luceneService;
        this.configuration = configuration;

        this.default_count = configuration.getLuceneResultCount();
    }

    @GetMapping("/{idLocation}")
    public GetLocationDTO getLocation(@PathVariable("idLocation") Integer idLocation) {
        return this.responseService
                .getLocationDTO(this.locationService.getLocation(idLocation));
    }

    @GetMapping("/search/custom")
    public void GetLocationByLongitude(@RequestParam(value="field") String field,
                                       @RequestParam(value="query") String query,
                                       @RequestParam(value="count", required = false) String countStr) {
        int count = default_count;
        if(countStr != null) {
            count = Math.min(Integer.parseInt(countStr), default_count);
        }
        luceneService.searchLocationWithCustom(field, query, count);
    }

    @GetMapping("/search/multiple")
    public void GetLocationByMultipleValue(
            @RequestParam(value="q_latitude", required = false) String q_latitude,

            @RequestParam(value="q_longitude", required = false) String q_longitude,

            @RequestParam(value="q_city", required = false) String q_city,

            @RequestParam(value="q_area", required = false) String q_area,

            @RequestParam(value="q_administrative_area_1", required = false) String q_administrative_area_1,

            @RequestParam(value="q_administrative_area_2", required = false) String q_administrative_area_2,

            @RequestParam(value="q_country", required = false) String q_country,

            @RequestParam(value="q_tags", required = false) String q_tags,

            @RequestParam(value="count", required = false) String countStr) {

        int count = default_count;
        if(countStr != null) {
            count = Math.min(Integer.parseInt(countStr), default_count);
        }

        luceneService.searchLocationWithMultipleValue(q_latitude, q_longitude, q_city, q_area, q_administrative_area_1, q_administrative_area_2, q_country, q_tags, count);
    }
}
