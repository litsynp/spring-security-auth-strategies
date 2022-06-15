package com.litsynp.springsecsession.domain.post.api;

import com.litsynp.springsecsession.domain.post.domain.Post;
import com.litsynp.springsecsession.domain.post.dto.PostCreateRequestDto;
import com.litsynp.springsecsession.domain.post.dto.PostMapper;
import com.litsynp.springsecsession.domain.post.dto.PostResponseDto;
import com.litsynp.springsecsession.domain.post.dto.PostUpdateRequestDto;
import com.litsynp.springsecsession.domain.post.service.PostService;
import java.net.URI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
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
    public ResponseEntity<PostResponseDto> updatePost(
            @PathVariable Long id, @Valid @RequestBody PostUpdateRequestDto dto) {
        Post updatedPost = postService.update(id, postMapper.toServiceDto(dto));
        PostResponseDto response = postMapper.toResponseDto(updatedPost);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        postService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
