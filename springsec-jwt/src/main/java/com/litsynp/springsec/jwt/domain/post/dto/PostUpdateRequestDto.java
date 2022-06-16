package com.litsynp.springsec.jwt.domain.post.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostUpdateRequestDto {

    @NotNull(message = "memberId cannot be null")
    private Long memberId;

    @Size(min = 1, max = 255, message = "The title must be between {min} and {max} characters long")
    private String title;

    @Size(min = 5, message = "The content must be at least {min} long")
    private String content;
}
