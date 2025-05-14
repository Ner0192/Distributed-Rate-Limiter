package com.ratelimiting.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
        @Bean
        public RouteLocator route(RouteLocatorBuilder routeLocatorBuilder) {
                return routeLocatorBuilder.routes()
                                .route(p -> p
                                                .path("/delUser/*")
                                                .uri("http://localhost:8081"))
                                .route(p -> p
                                                .path("/addUser")
                                                .uri("http://localhost:8081"))
                                .route(p -> p
                                                .path("/updateUser")
                                                .uri("http://localhost:8081"))
                                .route(p -> p
                                                .path("/getAllUser")
                                                .uri("http://localhost:8082"))
                                .route(p -> p
                                                .path("/getUserById/*")
                                                .uri("http://localhost:8082"))
                                .build();
        }
}
