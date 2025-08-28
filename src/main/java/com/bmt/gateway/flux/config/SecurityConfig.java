package com.bmt.gateway.flux.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationWebFilter jwtAuthenticationWebFilter;

    /**
     * Password Encoder for storing and verifying user passwords.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(ServerHttpSecurity.CorsSpec::disable) // you can enable with global CORS if needed
                .authorizeExchange(exchange -> exchange
                        // ✅ Swagger & API docs
                        .pathMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/auth-service/v3/api-docs",
                                "/user-service/v3/api-docs",
                                "/swagger-resources/**",
                                "/webjars/**",   // Swagger static
                                "/css/**",       // static CSS
                                "/js/**",        // static JS
                                "/images/**"     // static images
                        ).permitAll()

                        // ✅ Public auth-service APIs
                        .pathMatchers(
                                "/auth-service/v1/auth/login"
                        ).permitAll()

                        // ✅ Public user-service APIs
                        .pathMatchers(
                                "/user-service/v1/user/register",
                                "/user-service/v1/user/resend-otp",
                                "/user-service/v1/user/verify-otp"
                        ).permitAll()

                        // everything else requires authentication
                        .anyExchange().authenticated()
                )
                // Add your custom JWT filter
                .addFilterAt(jwtAuthenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION);
                // If you are using JWT based security
                //.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        return http.build();
    }
}

