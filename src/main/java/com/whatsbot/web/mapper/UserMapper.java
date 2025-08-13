package com.whatsbot.web.mapper;

import com.whatsbot.domain.Role;
import com.whatsbot.domain.User;
import com.whatsbot.web.dto.auth.UserSummaryDto;

import java.util.Set;
import java.util.stream.Collectors;

public final class UserMapper {
    private UserMapper() {}

    public static UserSummaryDto toSummary(User u) {
        Set<String> roleNames = u.getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        return new UserSummaryDto(
                u.getId(),
                u.getUsername(),
                u.getTenantKey(),
                u.getFirstName(),
                u.getLastName(),
                roleNames
        );
    }
}
