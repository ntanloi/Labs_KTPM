package com.cms.repository;

import com.cms.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByStatus(String status);
    List<Post> findByTitleContainingIgnoreCase(String keyword);
}
