package com.litsynp.springsec.oauth.domain.auth.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccessTokenRefreshRequestDto {

    @NotBlank(message = "refreshToken cannot be blank")
    private String refreshToken;
}
