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

    private int default_count;



    public LocationController(LocationService locationService,
                              ResponseService responseService,
                              LuceneService luceneService,
                              Configuration configuration) {
        this.locationService = locationService;
        this.responseService = responseService;
        this.luceneService = luceneService;
        this.configuration = configuration;

        default_count = configuration.getLuceneResultCount();
    }

    @GetMapping("/{idLocation}")
    public GetLocationDTO getLocation(@PathVariable("idLocation") Integer idLocation) {
        return this.responseService
                .getLocationDTO(this.locationService.getLocation(idLocation));
    }

    @GetMapping("/search/latitude")
    public void GetLocationByLatitude(@RequestParam(value="query") String query,
                          @RequestParam(value="count", required = false) String countStr) {

        int count = default_count;
        if(countStr != null) {
            count = Math.min(Integer.parseInt(countStr), default_count);
        }
        luceneService.searchLocationByLatitude(query, count);
    }

    @GetMapping("/search/longitude")
    public void GetLocationByLongitude(@RequestParam(value="query") String query,
                          @RequestParam(value="count", required = false) String countStr) {

        int count = default_count;
        if(countStr != null) {
            count = Math.min(Integer.parseInt(countStr), default_count);
        }
        luceneService.searchLocationByLongitude(query, count);
    }

    @GetMapping("/search/range")
    public void GetLocationByLongitude(@RequestParam(value="field") String field,
                                       @RequestParam(value="query") String query,
                                       @RequestParam(value="count", required = false) String countStr) {

        int count = default_count;
        if(countStr != null) {
            count = Math.min(Integer.parseInt(countStr), default_count);
        }

        luceneService.searchLocationByRange(field, query, count);

    }
}
