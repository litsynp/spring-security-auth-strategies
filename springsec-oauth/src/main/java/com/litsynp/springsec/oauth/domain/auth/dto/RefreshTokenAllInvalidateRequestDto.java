package com.litsynp.springsec.oauth.domain.auth.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenAllInvalidateRequestDto {

    @NotNull(message = "memberId cannot be null")
    private Long memberId;
}
