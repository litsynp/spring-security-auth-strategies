package com.litsynp.springsec.jwt.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostServiceUpdateRequestDto {

    private String title;
    private String content;
}
