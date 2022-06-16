package com.litsynp.springsec.jwt.domain.auth.dto;

import com.litsynp.springsec.jwt.domain.auth.vo.UserDetailsVo;
import com.litsynp.springsec.jwt.domain.member.domain.RoleType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public TokenResponseDto from(String token, UserDetailsVo userDetails) {
        return TokenResponseDto.builder()
                .accessToken(token)
                .member(from(userDetails))
                .build();
    }

    private AuthMemberResponseDto from(UserDetailsVo userDetailsVo) {
        return AuthMemberResponseDto.builder()
                .id(userDetailsVo.getId())
                .email(userDetailsVo.getVoEmail())
                .role(RoleType.fromValue(userDetailsVo.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .findFirst()
                        .orElse(RoleType.USER.getValue())))
                .build();
    }
}
