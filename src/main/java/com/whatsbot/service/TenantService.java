// src/main/java/com/whatsbot/service/TenantService.java
package com.whatsbot.service;

import com.whatsbot.domain.Tenant;
import com.whatsbot.repository.TenantRepository;
import com.whatsbot.web.dto.tenant.TenantCreateRequest;
import com.whatsbot.web.dto.tenant.TenantUpdateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class TenantService {

    private final TenantRepository repo;

    public TenantService(TenantRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public List<Tenant> findAll() {
        return repo.findAllByRecordStatus("A");
    }

    @Transactional
    public Tenant create(TenantCreateRequest req) {
        Tenant t = Tenant.builder()
                .id(UUID.randomUUID())
                .tenantCode(req.tenantCode())
                .name(req.name())
                .whatsappBusinessApiPhoneId(req.whatsappBusinessApiPhoneId())
                .metaAppId(req.metaAppId())
                .metaToken(req.metaToken())
                .build();
        return repo.save(t);
    }

    @Transactional
    public Tenant update(UUID id, TenantUpdateRequest req) {
        Tenant t = repo.findById(id).orElseThrow();
        if (req.tenantCode() != null && !req.tenantCode().isBlank()) {
            t.setTenantCode(req.tenantCode().trim());
        }
        if (req.name() != null) t.setName(req.name().trim());
        if (req.whatsappBusinessApiPhoneId() != null) t.setWhatsappBusinessApiPhoneId(req.whatsappBusinessApiPhoneId());
        if (req.metaAppId() != null) t.setMetaAppId(req.metaAppId());
        if (req.metaToken() != null) t.setMetaToken(req.metaToken());
        return repo.save(t);
    }

    @Transactional
    public void delete(UUID id) {
        Tenant t = repo.findById(id).orElseThrow();
        t.setRecordStatus("P");
        repo.save(t);
    }
}
