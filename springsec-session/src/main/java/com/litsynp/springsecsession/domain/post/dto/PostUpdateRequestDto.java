package com.litsynp.springsecsession.domain.post.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PostUpdateRequestDto {

    @NotNull(message = "memberId cannot be null")
    private Long memberId;

    @Size(min = 1, max = 255, message = "The title must be between {min} and {max} characters long")
    private String title;

    @Size(min = 5, message = "The content must be at least {min} long")
    private String content;
}
