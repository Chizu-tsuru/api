package com.chizu.tsuru.api.balancing.controllers;

import com.chizu.tsuru.api.balancing.services.BalancingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class BalancingController {
    private final BalancingService balancingService;

    public BalancingController(BalancingService balancingService) {
        this.balancingService = balancingService;
    }

    @GetMapping
    public String getIdentifier() {
        return this.balancingService.randomStr;
    }
}
