package com.whatsbot.service;

import com.whatsbot.domain.Tenant;
import com.whatsbot.repository.TenantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantService {

    private final TenantRepository tenantRepository;

    public TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    public List<Tenant> getAllTenants() {
        return tenantRepository.findAll();
    }

    public Tenant saveTenant(Tenant tenant) {
        return tenantRepository.save(tenant);
    }
}
