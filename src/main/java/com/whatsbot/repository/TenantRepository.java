package com.whatsbot.repository;

import com.whatsbot.domain.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TenantRepository extends JpaRepository<Tenant, UUID> {
    List<Tenant> findAllByRecordStatus(String recordStatus);
}
