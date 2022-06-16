package com.litsynp.springsec.jwt.domain.post.api;

import com.litsynp.springsec.jwt.domain.auth.service.AuthService;
import com.litsynp.springsec.jwt.domain.post.domain.Post;
import com.litsynp.springsec.jwt.domain.post.dto.PostCreateRequestDto;
import com.litsynp.springsec.jwt.domain.post.dto.PostMapper;
import com.litsynp.springsec.jwt.domain.post.dto.PostResponseDto;
import com.litsynp.springsec.jwt.domain.post.dto.PostUpdateRequestDto;
import com.litsynp.springsec.jwt.domain.post.service.PostService;
import java.net.URI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/v1/posts")
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;
    private final PostMapper postMapper;
    private final AuthService authService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<PostResponseDto> writePost(@Valid @RequestBody PostCreateRequestDto dto) {
        Post writtenPost = postService.create(postMapper.toServiceDto(dto));
        PostResponseDto response = postMapper.toResponseDto(writtenPost);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}").buildAndExpand(writtenPost.getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Page<PostResponseDto>> all(
            @PageableDefault(size = 20, sort = "createdOn", direction = Direction.DESC) Pageable pageable) {
        Page<PostResponseDto> response = postService.findAll(pageable)
                .map(postMapper::toResponseDto);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> one(@PathVariable Long id) {
        Post post = postService.findById(id);
        PostResponseDto response = postMapper.toResponseDto(post);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long id,
            @Valid @RequestBody PostUpdateRequestDto dto) {
        Post existing = postService.findById(id);
        authService.checkAuthorization(existing.getMember().getId());
        Post updated = postService.update(existing, postMapper.toServiceDto(dto));
        PostResponseDto response = postMapper.toResponseDto(updated);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Post existing = postService.findById(id);
        authService.checkAuthorization(existing.getMember().getId());
        postService.delete(existing);

        return ResponseEntity.noContent().build();
    }
}
