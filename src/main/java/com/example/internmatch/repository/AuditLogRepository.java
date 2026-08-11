package com.example.internmatch.repository;

import com.example.internmatch.entity.AuditLog;
import com.example.internmatch.enums.AuditActionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findByUserId(Long userId);
    List<AuditLog> findByAction(AuditActionType action);
    List<AuditLog> findByEntityNameAndEntityId(String entityName, Long entityId);
}
