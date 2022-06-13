package com.litsynp.springsecsession.domain.member.dto;

import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class MemberCreateRequestDto {

    @Size(min = 6, max = 345, message = "The email '${validatedValue}' must be between {min} and {max} characters long")
    private String email;

    @Size(min = 8, max = 16, message = "The password must be between {min} and {max} characters long")
    private String password;
}
