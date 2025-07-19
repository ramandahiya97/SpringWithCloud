package com.rest.webservices.spring_with_cloud.audit;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable {
    @Transient
    private static final Logger logger = LoggerFactory.getLogger(Auditable.class);
    protected Auditable(){
        logger.info("Auditable Entries will be initialized");
    }
    @CreatedDate
    private LocalDateTime createdDate;
    @CreatedBy
    private String createdBy;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
    @LastModifiedBy
    private String lastModifiedBy;
}
