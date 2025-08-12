package com.whatsbot.listener;

import com.whatsbot.domain.BaseEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuditEntityListener {

    @PrePersist
    public void prePersist(BaseEntity entity) {
        entity.setCreatedBy(getCurrentUser());
    }

    @PreUpdate
    public void preUpdate(BaseEntity entity) {
        entity.setUpdatedBy(getCurrentUser());
    }

    private String getCurrentUser() {
        try {
            var auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated()) {
                return auth.getName();
            }
        } catch (Exception ignored) {}
        return "system";
    }
}
