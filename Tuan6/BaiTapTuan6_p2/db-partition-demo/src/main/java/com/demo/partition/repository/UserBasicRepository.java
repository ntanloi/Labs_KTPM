package com.demo.partition.repository;

import com.demo.partition.entity.UserBasic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBasicRepository extends JpaRepository<UserBasic, Integer> {}
