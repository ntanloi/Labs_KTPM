package com.cms.service;

import com.cms.model.Post;
import com.cms.plugin.PluginRegistry;
import com.cms.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private PluginRegistry pluginRegistry;

    @InjectMocks
    private PostService postService;

    private Post samplePost;

    @BeforeEach
    void setUp() {
        samplePost = new Post();
        samplePost.setId(1L);
        samplePost.setTitle("Test Post");
        samplePost.setContent("Test Content");
        samplePost.setStatus("DRAFT");
    }

    @Test
    void getAllPosts_shouldReturnAllPosts() {
        when(postRepository.findAll()).thenReturn(List.of(samplePost));

        List<Post> result = postService.getAllPosts();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Test Post");
    }

    @Test
    void createPost_shouldSaveAndDispatchPlugin() {
        when(postRepository.save(any(Post.class))).thenReturn(samplePost);

        Post created = postService.createPost(samplePost);

        assertThat(created.getId()).isEqualTo(1L);
        verify(postRepository, times(1)).save(samplePost);
        verify(pluginRegistry, times(1)).dispatchPostCreate(samplePost);
    }

    @Test
    void getPostById_shouldReturnPost_whenExists() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(samplePost));

        Optional<Post> result = postService.getPostById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("Test Post");
    }

    @Test
    void getPostById_shouldReturnEmpty_whenNotExists() {
        when(postRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Post> result = postService.getPostById(99L);

        assertThat(result).isEmpty();
    }

    @Test
    void updatePost_shouldUpdateAndDispatchPlugin() {
        Post updated = new Post();
        updated.setTitle("Updated Title");
        updated.setContent("Updated Content");
        updated.setStatus("PUBLISHED");

        when(postRepository.findById(1L)).thenReturn(Optional.of(samplePost));
        when(postRepository.save(any(Post.class))).thenReturn(samplePost);

        Post result = postService.updatePost(1L, updated);

        verify(pluginRegistry, times(1)).dispatchPostUpdate(any());
        assertThat(result).isNotNull();
    }

    @Test
    void updatePost_shouldThrow_whenNotFound() {
        when(postRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> postService.updatePost(99L, samplePost))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Post not found");
    }

    @Test
    void deletePost_shouldDeleteAndDispatchPlugin() {
        doNothing().when(postRepository).deleteById(1L);

        postService.deletePost(1L);

        verify(postRepository, times(1)).deleteById(1L);
        verify(pluginRegistry, times(1)).dispatchPostDelete(1L);
    }

    @Test
    void publishPost_shouldChangeStatusToPublished() {
        when(postRepository.findById(1L)).thenReturn(Optional.of(samplePost));
        when(postRepository.save(any(Post.class))).thenAnswer(inv -> inv.getArgument(0));

        Post published = postService.publishPost(1L);

        assertThat(published.getStatus()).isEqualTo("PUBLISHED");
    }
}
