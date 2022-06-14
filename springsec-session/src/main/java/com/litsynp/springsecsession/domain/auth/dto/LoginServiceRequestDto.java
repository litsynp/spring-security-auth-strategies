package com.litsynp.springsecsession.domain.auth.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginServiceRequestDto {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
