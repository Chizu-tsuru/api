package com.chizu.tsuru.api.stats.controllers;

import com.chizu.tsuru.api.stats.dto.CreateStatDTO;
import com.chizu.tsuru.api.stats.dto.GetStatDTO;
import com.chizu.tsuru.api.stats.services.StatDTOService;
import com.chizu.tsuru.api.stats.services.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/stats")
public class StatController {

    private final StatService statService;
    private final StatDTOService statDTOService;

    @Autowired
    public StatController(StatService statService, StatDTOService statDTOService) {
        this.statService = statService;
        this.statDTOService = statDTOService;
    }

    @GetMapping
    public Page<GetStatDTO> getStats(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int limit,
                                     @RequestParam(defaultValue = "date") String sort,
                                     @RequestParam(defaultValue = "desc") String direction) {
        return this.statDTOService.toResponse(this.statService.getStats(page, limit, sort, direction));
    }

    @PostMapping
    public GetStatDTO createStat(@Valid @RequestBody CreateStatDTO statDTO) {
        return this.statDTOService.toResponse(this.statService.createStat(statDTO));
    }
}
