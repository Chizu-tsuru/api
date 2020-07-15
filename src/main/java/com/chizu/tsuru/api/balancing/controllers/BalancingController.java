package com.chizu.tsuru.api.balancing.controllers;

import com.chizu.tsuru.api.balancing.services.BalancingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

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

    @GetMapping("/requests")
    public Map<String, String> getHeader(HttpServletRequest request){
        Map<String, String> map = new HashMap<String, String>();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }

        return map;
    }
    @GetMapping("/requests2")
    public Map<String, String> getHeaders(@RequestHeader Map<String, String> headers){

        return headers;
    }
}
