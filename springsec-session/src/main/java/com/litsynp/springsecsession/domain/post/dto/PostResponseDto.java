package com.litsynp.springsecsession.domain.post.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PostResponseDto {

    @NotNull
    private Long id;

    @NotNull
    private Long memberId;

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    @NotNull
    private LocalDateTime createdOn;

    @NotNull
    private LocalDateTime updatedOn;
}
