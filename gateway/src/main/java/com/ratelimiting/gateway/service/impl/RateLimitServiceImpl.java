package com.ratelimiting.gateway.service.impl;

import com.ratelimiting.gateway.RateLimit;
import com.ratelimiting.gateway.RateLimitRepository;
import com.ratelimiting.gateway.service.RateLimitService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class RateLimitServiceImpl implements RateLimitService {

    private final RateLimitRepository rateLimitRepository;

    public RateLimitServiceImpl(RateLimitRepository rateLimitRepository) {
        this.rateLimitRepository = rateLimitRepository;
    }

    @Override
    public Mono<RateLimit> findByClientIpAddressAndCustomParameterAndEndPointPath(String clientIpAddress,
            String customParameter, String endPointPath) {
        return rateLimitRepository.findByClientIpAddressAndCustomParameterAndEndPointPath(clientIpAddress,
                customParameter, endPointPath);
    }

    @Override
    public Mono<RateLimit> save(RateLimit rateLimit) {
        return rateLimitRepository.save(rateLimit);
    }
}
