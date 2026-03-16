package com.cms.controller;

import com.cms.model.Post;
import com.cms.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

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
    void getAllPosts_shouldReturn200() throws Exception {
        when(postService.getAllPosts()).thenReturn(List.of(samplePost));

        mockMvc.perform(get("/api/posts"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].title").value("Test Post"))
            .andExpect(jsonPath("$[0].status").value("DRAFT"));
    }

    @Test
    void createPost_shouldReturn201() throws Exception {
        when(postService.createPost(any(Post.class))).thenReturn(samplePost);

        mockMvc.perform(post("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(samplePost)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.title").value("Test Post"));
    }

    @Test
    void getPostById_shouldReturn200_whenFound() throws Exception {
        when(postService.getPostById(1L)).thenReturn(Optional.of(samplePost));

        mockMvc.perform(get("/api/posts/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Test Post"));
    }

    @Test
    void getPostById_shouldReturn404_whenNotFound() throws Exception {
        when(postService.getPostById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/posts/99"))
            .andExpect(status().isNotFound());
    }

    @Test
    void deletePost_shouldReturn200() throws Exception {
        mockMvc.perform(delete("/api/posts/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Post deleted successfully"));
    }

    @Test
    void publishPost_shouldReturn200_andStatusPublished() throws Exception {
        samplePost.setStatus("PUBLISHED");
        when(postService.publishPost(1L)).thenReturn(samplePost);

        mockMvc.perform(patch("/api/posts/1/publish"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("PUBLISHED"));
    }
}
