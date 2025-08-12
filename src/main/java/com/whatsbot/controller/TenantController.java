package com.whatsbot.controller;

import com.whatsbot.domain.Tenant;
import com.whatsbot.service.TenantService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @GetMapping
    public List<Tenant> getAllTenants() {
        System.out.println("sa as");
        return tenantService.getAllTenants();
    }

    @PostMapping
    public Tenant createTenant(@RequestBody Tenant tenant) {
        return tenantService.saveTenant(tenant);
    }
}
