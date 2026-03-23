package com.demo.partition.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "func_audit_logs")
public class FuncAuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String action;
    private String tableName;
    private String performedBy;
    private LocalDateTime logTime;
}
