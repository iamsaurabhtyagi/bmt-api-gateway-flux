package com.bmt.gateway.flux.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutesConfig {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes()
                // Route for auth-service (includes APIs + v3/api-docs)
                .route("auth-service", r -> r.path("/auth-service/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://localhost:8080"))

                // Route for auth-service (includes APIs + v3/api-docs)
                .route("user-service", r -> r.path("/user-service/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://localhost:8081"))

                .build();
    }
}
