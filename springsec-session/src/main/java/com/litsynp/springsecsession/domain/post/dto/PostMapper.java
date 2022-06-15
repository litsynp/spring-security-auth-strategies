package com.litsynp.springsecsession.domain.post.dto;

import com.litsynp.springsecsession.domain.post.domain.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public PostResponseDto toResponseDto(Post post) {
        return PostResponseDto.builder()
                .id(post.getId())
                .memberId(post.getMember().getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdOn(post.getCreatedOn())
                .updatedOn(post.getUpdatedOn())
                .build();
    }

    public PostServiceCreateRequestDto toServiceDto(PostCreateRequestDto apiDto) {
        return PostServiceCreateRequestDto.builder()
                .memberId(apiDto.getMemberId())
                .title(apiDto.getTitle())
                .content(apiDto.getContent())
                .build();
    }

    public PostServiceUpdateRequestDto toServiceDto(PostUpdateRequestDto apiDto) {
        return PostServiceUpdateRequestDto.builder()
                .title(apiDto.getTitle())
                .content(apiDto.getContent())
                .build();
    }
}
