package com.app.rakbank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions.sameOrigin()) // Allow iframes from the same origin
            )
            .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().permitAll() // Allow all requests without authentication
            );
        return http.build();
    }
}
