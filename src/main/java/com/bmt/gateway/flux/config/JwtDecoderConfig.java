package com.bmt.gateway.flux.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;

@Configuration
public class JwtDecoderConfig {

    private static final String SIGNING_KEY = "your-secret-key-saurabh-kumar-tyagi-140288";

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        // For symmetric HMAC secret
        return NimbusReactiveJwtDecoder.withSecretKey(
                new javax.crypto.spec.SecretKeySpec(SIGNING_KEY.getBytes(), "HmacSHA256")
        ).build();
    }
}
