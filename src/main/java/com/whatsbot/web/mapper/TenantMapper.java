package com.whatsbot.web.mapper;

import com.whatsbot.domain.Tenant;
import com.whatsbot.web.dto.tenant.TenantDto;

public final class TenantMapper {
    private TenantMapper() {}

    public static TenantDto toDto(Tenant t) {
        return new TenantDto(
                t.getId(),
                t.getTenantCode(),
                t.getName(),
                t.getWhatsappBusinessApiPhoneId(),
                t.getMetaAppId(),
                t.getMetaToken()
        );
    }
}
