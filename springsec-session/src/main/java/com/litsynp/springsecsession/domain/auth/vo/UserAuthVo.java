package com.litsynp.springsecsession.domain.auth.vo;

import com.litsynp.springsecsession.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class UserAuthVo {

    private String email;
    private String password;
    private Boolean active;

    public String getVoEmail() {
        return email;
    }

    public String getVoPassword() {
        return password;
    }

    public Boolean getVoActive() {
        return active;
    }

    public static UserAuthVo fromEntity(Member member) {
        return UserAuthVo.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .active(member.getActive())
                .build();
    }
}
