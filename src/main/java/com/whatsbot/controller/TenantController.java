// src/main/java/com/whatsbot/controller/TenantController.java
package com.whatsbot.controller;

import com.whatsbot.domain.Tenant;
import com.whatsbot.service.TenantService;
import com.whatsbot.web.dto.tenant.TenantCreateRequest;
import com.whatsbot.web.dto.tenant.TenantDto;
import com.whatsbot.web.dto.tenant.TenantUpdateRequest;
import com.whatsbot.web.mapper.TenantMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {

    private final TenantService service;

    public TenantController(TenantService service) {
        this.service = service;
    }

    @GetMapping
    public List<TenantDto> all() {
        return service.findAll().stream().map(TenantMapper::toDto).toList();
    }

    @PostMapping
    public ResponseEntity<TenantDto> create(@Valid @RequestBody TenantCreateRequest req) {
        Tenant t = service.create(req);
        return ResponseEntity.ok(TenantMapper.toDto(t));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TenantDto> update(@PathVariable UUID id, @Valid @RequestBody TenantUpdateRequest req) {
        Tenant t = service.update(id, req);
        return ResponseEntity.ok(TenantMapper.toDto(t));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
