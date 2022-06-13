package com.litsynp.springsecsession.domain.member.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MemberResponseDto {

    @NotNull
    private Long id;

    @NotEmpty
    private String email;

    @NotNull
    private LocalDateTime createdOn;

    @NotNull
    private LocalDateTime updatedOn;
}
