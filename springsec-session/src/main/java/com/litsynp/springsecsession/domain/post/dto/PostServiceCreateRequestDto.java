package com.litsynp.springsecsession.domain.post.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PostServiceCreateRequestDto {

    private Long memberId;
    private String title;
    private String content;
}
