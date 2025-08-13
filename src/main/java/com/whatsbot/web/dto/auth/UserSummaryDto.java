package com.whatsbot.web.dto.auth;

import java.util.Set;

public record UserSummaryDto(
        Long id,
        String username,
        String tenantKey,
        String firstName,
        String lastName,
        Set<String> roles
) {}
