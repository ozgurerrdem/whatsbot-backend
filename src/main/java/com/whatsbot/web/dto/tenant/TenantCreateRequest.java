package com.whatsbot.web.dto.tenant;

import jakarta.validation.constraints.NotBlank;

public record TenantCreateRequest(
        @NotBlank String tenantCode,
        @NotBlank String name,
        String whatsappBusinessApiPhoneId,
        String metaAppId,
        String metaToken
) {}
