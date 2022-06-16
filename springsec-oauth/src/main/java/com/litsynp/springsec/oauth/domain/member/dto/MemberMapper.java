package com.litsynp.springsec.oauth.domain.member.dto;

import com.litsynp.springsec.oauth.domain.member.domain.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public Member toEntity(MemberCreateRequestDto dto) {
        return Member.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }

    public MemberResponseDto toResponseDto(Member member) {
        return MemberResponseDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .createdOn(member.getCreatedOn())
                .updatedOn(member.getUpdatedOn())
                .build();
    }
}
