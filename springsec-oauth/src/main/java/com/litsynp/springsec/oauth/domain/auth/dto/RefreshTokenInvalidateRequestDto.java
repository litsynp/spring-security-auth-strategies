package com.litsynp.springsec.oauth.domain.auth.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenInvalidateRequestDto {

    @NotNull(message = "memberId cannot be null")
    private Long memberId;

    @NotBlank(message = "refreshToken cannot be blank")
    private String refreshToken;
}
