package com.whatsbot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Value("${app.security.enabled:true}") // Varsayılan prod
    private boolean securityEnabled;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());

        if (!securityEnabled) {
            // DEV → herkes erişebilir
            http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        } else {
            // PROD → her endpoint login gerektirir
            http.authorizeHttpRequests(auth -> auth
                    .anyRequest().authenticated()
            ).httpBasic(Customizer.withDefaults());
        }

        return http.build();
    }
}
