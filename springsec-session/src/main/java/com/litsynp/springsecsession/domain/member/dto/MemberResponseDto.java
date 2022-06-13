package com.litsynp.springsecsession.domain.member.dto;

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
}
