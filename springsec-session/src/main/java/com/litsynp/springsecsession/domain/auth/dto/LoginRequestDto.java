package com.litsynp.springsecsession.domain.auth.dto;

import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {

    @Size(min = 6, max = 345, message = "The email must be between {min} and {max} characters long")
    private String email;

    @Size(min = 8, max = 16, message = "The password must be between {min} and {max} characters long")
    private String password;
}
