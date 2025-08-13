package com.whatsbot.common;

import com.whatsbot.security.PrincipalUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AppContext {
    public Optional<PrincipalUser> getUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof PrincipalUser pu) {
            return Optional.of(pu);
        }
        return Optional.empty();
    }

    public String usernameOrSystem() {
        return getUser().map(PrincipalUser::username).orElse("system");
    }

    public String tenantOrNull() {
        return getUser().map(PrincipalUser::tenantKey).orElse(null);
    }
}
