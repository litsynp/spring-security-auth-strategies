package com.litsynp.springsecsession.domain.post.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
