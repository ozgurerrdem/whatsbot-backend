package com.whatsbot.config;

import com.whatsbot.domain.Tenant;
import com.whatsbot.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final TenantRepository tenantRepo;

    @Override
    public void run(String... args) {
        if (tenantRepo.count() == 0) {
            Tenant tenant = Tenant.builder()
                    .id(UUID.randomUUID())
                    .name("Test Kurum")
                    .whatsappBusinessApiPhoneId("1234567890")
                    .metaAppId("metaAppId123")
                    .metaToken("metaToken123")
                    .build();
            tenantRepo.save(tenant);
            System.out.println("Tenant kaydı eklendi!");
        }
    }
}
