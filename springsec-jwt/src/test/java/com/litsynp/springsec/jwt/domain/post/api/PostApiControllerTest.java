package com.litsynp.springsec.jwt.domain.post.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.litsynp.springsec.jwt.config.SpringSecurityWebAuxTestConfig;
import com.litsynp.springsec.jwt.domain.post.domain.Post;
import com.litsynp.springsec.jwt.domain.post.dto.PostCreateRequestDto;
import com.litsynp.springsec.jwt.domain.post.dto.PostMapper;
import com.litsynp.springsec.jwt.domain.post.dto.PostResponseDto;
import com.litsynp.springsec.jwt.domain.post.dto.PostServiceUpdateRequestDto;
import com.litsynp.springsec.jwt.domain.post.dto.PostUpdateRequestDto;
import com.litsynp.springsec.jwt.domain.post.service.PostService;
import com.litsynp.springsec.jwt.template.ApiMockControllerTest;
import com.litsynp.springsec.jwt.util.FieldUtil;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@WebMvcTest(PostApiController.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Import({PostMapper.class})
class PostApiControllerTest extends ApiMockControllerTest {

    @Autowired
    private PostMapper postMapper;

    @MockBean
    private PostService postService;

    @Test
    @DisplayName("Write post - Ok")
    @WithMockUser("testuser@example.com")
    void writePost_ok() throws Exception {
        // given
        PostCreateRequestDto requestDto = new PostCreateRequestDto(1L, "Test title",
                "Test content");

        Post post = Post.builder()
                .member(SpringSecurityWebAuxTestConfig.getBasicMember())
                .title("Test title")
                .content("Test content")
                .build();

        Post created = Post.builder()
                .member(SpringSecurityWebAuxTestConfig.getBasicMember())
                .title("Test title")
                .content("Test content")
                .build();
        FieldUtil.writeField(created, "id", 1L);

        given(postService.create(any()))
                .willReturn(created);

        // when & then
        mockMvc.perform(post("/v1/posts")
                        .content(objectMapper.writeValueAsString(requestDto))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(
                        objectMapper.writeValueAsString(postMapper.toResponseDto(created))));
    }

    @Test
    @DisplayName("Get post list - Ok")
    void getAll_ok() throws Exception {
        // given
        List<Post> content = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            Post post = Post.builder()
                    .member(SpringSecurityWebAuxTestConfig.getBasicMember())
                    .title("Test title")
                    .content("Test content")
                    .build();
            content.add(post);
        }
        Page<Post> svcResponse = new PageImpl<>(content, PageRequest.of(0, 10), content.size());
        Page<PostResponseDto> response = svcResponse.map(postMapper::toResponseDto);

        given(postService.findAll(any()))
                .willReturn(svcResponse);

        // when & then
        mockMvc.perform(get("/v1/posts")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string(objectMapper.writeValueAsString(response)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Get one post - Ok")
    void getOne_ok() throws Exception {
        // given
        Post post = Post.builder()
                .member(SpringSecurityWebAuxTestConfig.getBasicMember())
                .title("Test title")
                .content("Test content")
                .build();
        FieldUtil.writeField(post, "id", 1L);

        PostResponseDto response = postMapper.toResponseDto(post);

        given(postService.findById(1L))
                .willReturn(post);

        // when & then
        mockMvc.perform(get("/v1/posts/{id}", post.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().string(objectMapper.writeValueAsString(response)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Update post - Ok")
    @WithMockUser("testuser@example.com")
    void updatePost_ok() throws Exception {
        // given
        PostUpdateRequestDto requestDto = new PostUpdateRequestDto(
                SpringSecurityWebAuxTestConfig.getBasicMember().getId(),
                "Test title updated", "Test content updated");

        Post existing = Post.builder()
                .member(SpringSecurityWebAuxTestConfig.getBasicMember())
                .title("Test title")
                .content("Test content")
                .build();
        FieldUtil.writeField(existing, "id", 1L);

        Post updated = Post.builder()
                .member(SpringSecurityWebAuxTestConfig.getBasicMember())
                .title("Test title updated")
                .content("Test content updated")
                .build();
        FieldUtil.writeField(updated, "id", 1L);

        given(postService.findById(existing.getId()))
                .willReturn(existing);

        given(postService.update(eq(existing), any(PostServiceUpdateRequestDto.class)))
                .willReturn(updated);

        // when & then
        mockMvc.perform(put("/v1/posts/{id}", existing.getId())
                        .content(objectMapper.writeValueAsString(requestDto))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(
                        objectMapper.writeValueAsString(postMapper.toResponseDto(updated))));
    }

    @Test
    @DisplayName("Delete post - Ok")
    @WithMockUser("testuser@example.com")
    void deletePost_ok() throws Exception {
        // given
        Post existing = Post.builder()
                .member(SpringSecurityWebAuxTestConfig.getBasicMember())
                .title("Test title")
                .content("Test content")
                .build();
        FieldUtil.writeField(existing, "id", 1L);

        given(postService.findById(existing.getId()))
                .willReturn(existing);

        // when & then
        mockMvc.perform(delete("/v1/posts/{id}", existing.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
