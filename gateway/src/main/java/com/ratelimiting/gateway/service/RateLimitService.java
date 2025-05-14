package com.ratelimiting.gateway.service;

import com.ratelimiting.gateway.RateLimit;
import reactor.core.publisher.Mono;

public interface RateLimitService {
    Mono<RateLimit> findByClientIpAddressAndCustomParameterAndEndPointPath(String clientIpAddress,
            String customParameter, String endPointPath);

    Mono<RateLimit> save(RateLimit rateLimit);
}
