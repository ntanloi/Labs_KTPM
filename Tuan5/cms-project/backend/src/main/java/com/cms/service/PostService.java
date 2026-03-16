package com.cms.service;

import com.cms.model.Post;
import com.cms.repository.PostRepository;
import com.cms.plugin.PluginRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Layer - Business logic for Post management
 */
@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final PluginRegistry pluginRegistry;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public List<Post> getPublishedPosts() {
        return postRepository.findByStatus("PUBLISHED");
    }

    public List<Post> searchPosts(String keyword) {
        return postRepository.findByTitleContainingIgnoreCase(keyword);
    }

    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    public Post createPost(Post post) {
        Post saved = postRepository.save(post);
        pluginRegistry.dispatchPostCreate(saved);
        return saved;
    }

    public Post updatePost(Long id, Post postData) {
        return postRepository.findById(id).map(existing -> {
            existing.setTitle(postData.getTitle());
            existing.setContent(postData.getContent());
            existing.setStatus(postData.getStatus());
            Post updated = postRepository.save(existing);
            pluginRegistry.dispatchPostUpdate(updated);
            return updated;
        }).orElseThrow(() -> new RuntimeException("Post not found: " + id));
    }

    public Post publishPost(Long id) {
        return postRepository.findById(id).map(post -> {
            post.setStatus("PUBLISHED");
            Post published = postRepository.save(post);
            pluginRegistry.dispatchPostUpdate(published);
            return published;
        }).orElseThrow(() -> new RuntimeException("Post not found: " + id));
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
        pluginRegistry.dispatchPostDelete(id);
    }
}
