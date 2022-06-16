package com.litsynp.springsec.jwt.domain.auth.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccessTokenValidateRequestDto {

    @NotBlank(message = "accessToken cannot be blank")
    private String accessToken;
}
