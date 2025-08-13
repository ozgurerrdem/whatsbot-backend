package com.whatsbot.web.dto.tenant;

import java.util.UUID;

public record TenantDto(
        UUID id,
        String tenantCode,
        String name,
        String whatsappBusinessApiPhoneId,
        String metaAppId,
        String metaToken
) {}

