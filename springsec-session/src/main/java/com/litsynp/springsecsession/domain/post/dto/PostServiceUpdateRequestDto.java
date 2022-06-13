package com.litsynp.springsecsession.domain.post.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PostServiceUpdateRequestDto {

    private String title;
    private String content;
}
