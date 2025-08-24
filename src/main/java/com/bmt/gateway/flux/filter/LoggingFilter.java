package com.bmt.gateway.flux.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.WebFilter;

@Configuration
public class LoggingFilter {
    @Bean
    public WebFilter customLoggingFilter() {
        return (exchange, chain) -> {
            System.out.println("👉 Incoming request: " + exchange.getRequest().getMethod() + " " + exchange.getRequest().getURI());
            return chain.filter(exchange).doOnError(err -> {
                System.err.println("❌ Security error: " + err.getMessage());
            });
        };
    }
}
