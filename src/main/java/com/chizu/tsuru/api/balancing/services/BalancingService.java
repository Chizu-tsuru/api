package com.chizu.tsuru.api.balancing.services;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BalancingService {
    public final String randomStr;

    public BalancingService() {
        this.randomStr = UUID.randomUUID().toString();
    }
}
