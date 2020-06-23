package com.chizu.tsuru.api.clusters.controllers;

import com.chizu.tsuru.api.clusters.dto.GetLocationDTO;
import com.chizu.tsuru.api.clusters.dto.GetLocationLuceneDTO;
import com.chizu.tsuru.api.clusters.services.LocationService;
import com.chizu.tsuru.api.clusters.services.LuceneService;
import com.chizu.tsuru.api.config.Configuration;
import com.chizu.tsuru.api.shared.services.ResponseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<GetLocationLuceneDTO> GetLocationByLongitude(@RequestParam(value="field") String field,
                                       @RequestParam(value="query") String query,
                                       @RequestParam(value="count", required = false) String countStr) {
        int count = default_count;
        if(countStr != null) {
            count = Math.min(Integer.parseInt(countStr), default_count);
        }
        return luceneService.searchLocationWithCustom(field, query, count);
    }

    @GetMapping("/search/multiple")
    public List<GetLocationLuceneDTO> GetLocationByMultipleValue(
            @RequestParam(value="latitude", required = false) String q_latitude,

            @RequestParam(value="longitude", required = false) String q_longitude,

            @RequestParam(value="city", required = false) String q_city,

            @RequestParam(value="area", required = false) String q_area,

            @RequestParam(value="administrative_area_1", required = false) String q_administrative_area_1,

            @RequestParam(value="administrative_area_2", required = false) String q_administrative_area_2,

            @RequestParam(value="country", required = false) String q_country,

            @RequestParam(value="tags", required = false) String q_tags,

            @RequestParam(value="count", required = false) String countStr) {

        int count = default_count;
        if(countStr != null) {
            count = Math.min(Integer.parseInt(countStr), default_count);
        }

        return responseService.getLocationLuceneDTO(
                luceneService.searchLocationWithMultipleValue(q_latitude,
                        q_longitude,
                        q_city,
                        q_area,
                        q_administrative_area_1,
                        q_administrative_area_2,
                        q_country,
                        q_tags,
                        count));
    }
}
