package com.whatsbot.config;

import com.whatsbot.common.AppContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AuditingConfig {

    private final AppContext appContext;

    public AuditingConfig(AppContext appContext) {
        this.appContext = appContext;
    }

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of(appContext.usernameOrSystem());
    }
}
