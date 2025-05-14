package com.ratelimiting.gateway;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;

@Repository
public interface RateLimitRepository extends ReactiveMongoRepository<RateLimit, String> {

    Mono<RateLimit> findByClientIpAddressAndCustomParameterAndEndPointPath(String clientIpAddress,
            String customParameter, String endPointPath);

}
