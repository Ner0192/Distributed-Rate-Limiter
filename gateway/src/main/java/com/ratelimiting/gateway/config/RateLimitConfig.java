package com.ratelimiting.gateway.config;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RateLimitConfig {

    @Value("${rate.limit.maxRequestsPerMinute}")
    private int MAX_REQUESTS_PER_MINUTE;

    private final Map<String, Integer> endpointRateLimits = new HashMap<>();

    public RateLimitConfig() {
        endpointRateLimits.put("/delUser", 5);
        endpointRateLimits.put("/addUser", 10);
        endpointRateLimits.put("/updateUser", 8);
        endpointRateLimits.put("/getAllUser", 15);
        endpointRateLimits.put("/getUserById", 12);
    }

    public int getRateLimitForEndpoint(String basePath) {
        return endpointRateLimits.getOrDefault(basePath, MAX_REQUESTS_PER_MINUTE);
    }
}
