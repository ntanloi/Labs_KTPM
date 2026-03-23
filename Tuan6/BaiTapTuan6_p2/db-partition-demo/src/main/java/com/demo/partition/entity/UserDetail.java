package com.demo.partition.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_detail")
public class UserDetail {

    @Id
    private Integer id;

    private String address;
    private String phone;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String bio;

    private String avatarUrl;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private UserBasic userBasic;
}
