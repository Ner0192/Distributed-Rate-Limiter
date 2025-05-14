package com.ratelimiting.gateway;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.ratelimiting.gateway.config.RateLimitConfig;

import reactor.core.publisher.Mono;

@Component
public class GlobalRateLimitingFilter implements GlobalFilter, Ordered {

    @Autowired
    private RateLimitRepository rateLimitService;

    @Autowired
    private RateLimitConfig rateLimitConfig;

    @Value("${rate.limit.resetTimeInSeconds}")
    private int RESET_TIME_IN_SECONDS;

    private String getBasePath(String fullPath) {
        fullPath = fullPath.replaceAll("/$", "");
        fullPath = fullPath.replaceAll("/\\d+$", "").replaceAll("/[a-fA-F0-9\\-]{24}$", "");
        return fullPath;
    }

    @Override
    public Mono<Void> filter(org.springframework.web.server.ServerWebExchange exchange,
            org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {

        String clientIpAddress = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
        AtomicBoolean ipExist = new AtomicBoolean(false);
        String header = exchange.getRequest().getHeaders().getFirst("X-Custom-Parameter");
        String customParameter = header != null ? header : "default";
        String method = exchange.getRequest().getMethod().toString();
        String basePath = getBasePath(exchange.getRequest().getPath().toString());
        String endPointPath = method + ":" + basePath;
        int MAX_REQUESTS_PER_MINUTE = rateLimitConfig.getRateLimitForEndpoint(basePath);

        return rateLimitService
                .findByClientIpAddressAndCustomParameterAndEndPointPath(clientIpAddress, customParameter, endPointPath)
                .flatMap(rateLimit -> {
                    Instant lastRequestTime = rateLimit.getLastRequestTime();
                    ipExist.set(true);

                    if (Instant.now().isAfter(lastRequestTime.plusSeconds(RESET_TIME_IN_SECONDS))) {
                        rateLimit.setRequestCount(0);
                        rateLimit.setLastRequestTime(Instant.now());
                    }

                    int requests = rateLimit.getRequestCount() + 1;
                    rateLimit.setRequestCount(requests);

                    if (requests > MAX_REQUESTS_PER_MINUTE) {
                        exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                        exchange.getResponse().getHeaders().add("Retry-After", String.valueOf(RESET_TIME_IN_SECONDS));
                        String body = "{\"error\":\"Too many requests. Please try again later.\"}";
                        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
                        return exchange.getResponse()
                                .writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(bytes)));
                    }

                    return rateLimitService.save(rateLimit).then(chain.filter(exchange));
                })
                .switchIfEmpty(Mono.defer(() -> {
                    if (!ipExist.get()) {
                        RateLimit newRateLimit = new RateLimit(clientIpAddress, customParameter, 1, Instant.now(),
                                endPointPath);
                        return rateLimitService.save(newRateLimit).then(chain.filter(exchange));
                    }
                    return chain.filter(exchange);
                }));

    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
