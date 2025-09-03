package com.whatsbot.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(
        name = "tenants",
        uniqueConstraints = @UniqueConstraint(name = "ux_tenants_tenant_code", columnNames = "tenant_code")
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tenant extends BaseEntity {

    @Id
    private UUID id;

    @Column(name = "tenant_code", nullable = false, length = 64)
    private String tenantCode;

    private String name;
    private String whatsappBusinessApiPhoneId;
    private String metaAppId;
    private String metaToken;
}
