package com.whatsbot.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "tenants")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Tenant extends BaseEntity {

    @Id
    private UUID id;

    private String name;         // kurum adı
    private String whatsappBusinessApiPhoneId;  // WhatsApp Business API phone id
    private String metaAppId;    // Meta App id
    private String metaToken;    // API erişim token
}