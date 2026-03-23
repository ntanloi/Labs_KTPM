package com.demo.partition.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Entity dùng chung cho table_user_01 và table_user_02.
 * Table name sẽ được set động khi query bằng native SQL.
 */
@Data
public class UserHorizontal {
    private Integer id;
    private String name;
    private String email;
    private String gender;
    private Integer age;
    private String city;
}
