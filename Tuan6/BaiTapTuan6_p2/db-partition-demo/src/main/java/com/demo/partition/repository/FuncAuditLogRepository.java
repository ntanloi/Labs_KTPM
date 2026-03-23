package com.demo.partition.repository;

import com.demo.partition.entity.FuncAuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncAuditLogRepository extends JpaRepository<FuncAuditLog, Integer> {}
