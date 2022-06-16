package com.litsynp.springsec.jwt.domain.auth.dto;

import com.litsynp.springsec.jwt.domain.member.domain.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthMemberResponseDto {

    private Long id;
    private String email;
    private RoleType role;
}
