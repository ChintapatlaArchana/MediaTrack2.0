package com.cts.config;

import com.cts.jwtauth.JwtAuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    private final JwtAuthenticationFilter jwtFilter;

    public GatewayConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-authentication", r-> r.path("/user/**")
                        .uri("http://localhost:9090"))
                .route("subscription", r-> r.path("/subscription/**", "/plan/**", "/entitlement/**")
                        .filters(f -> f.filter(jwtFilter))
                        .uri("http://localhost:7070"))
                .route("content", r-> r.path("/asset/**","/categories/**","/titles/**","/versions/**")
                        .uri("http://localhost:8090"))
                .route("notification", r-> r.path("//**")
                        .filters(f-> f.filter(jwtFilter))
                        .uri("http://localhost:8098"))
                .route("analytics", r-> r.path("/adDeliveryReport/**","/churnCohort/**","/engagementReport/**")
                        .filters(f -> f.filter(jwtFilter))
                        .uri("http://localhost:8083"))
                .build();
    }
}
