package com.whatsbot.web.dto.tenant;

public record TenantUpdateRequest(
        String tenantCode,
        String name,
        String whatsappBusinessApiPhoneId,
        String metaAppId,
        String metaToken
) {}
