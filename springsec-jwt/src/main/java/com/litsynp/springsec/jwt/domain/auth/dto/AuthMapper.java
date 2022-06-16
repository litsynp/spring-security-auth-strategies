package com.litsynp.springsec.jwt.domain.auth.dto;

import com.litsynp.springsec.jwt.domain.auth.vo.UserDetailsVo;
import com.litsynp.springsec.jwt.domain.member.domain.Member;
import com.litsynp.springsec.jwt.domain.member.domain.RoleType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public TokenResponseDto from(String accessToken, String refreshToken,
            UserDetailsVo userDetails) {
        return TokenResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .member(from(userDetails))
                .build();
    }

    public TokenResponseDto from(String accessToken, String refreshToken,
            Member member) {
        return TokenResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .member(from(member))
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

    private AuthMemberResponseDto from(Member member) {
        return AuthMemberResponseDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .role(member.getRole())
                .build();
    }
}
