package com.demo.partition.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_basic")
public class UserBasic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String email;
    private String gender;

    @OneToOne(mappedBy = "userBasic", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserDetail detail;
}
