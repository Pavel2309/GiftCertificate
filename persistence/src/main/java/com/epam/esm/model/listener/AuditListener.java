package com.epam.esm.model.listener;

import com.epam.esm.model.entity.CommonEntity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.Instant;

public class AuditListener {

    @PrePersist
    public void onPrePersist(CommonEntity entity) {
        audit("INSERT", entity);
    }

    @PreUpdate
    public void onPreUpdate(CommonEntity entity) {
        audit("UPDATE", entity);
    }

    private void audit(String operation, CommonEntity entity) {
        entity.setOperation(operation);
        entity.setTimestamp(Instant.now().getEpochSecond());
    }
}
