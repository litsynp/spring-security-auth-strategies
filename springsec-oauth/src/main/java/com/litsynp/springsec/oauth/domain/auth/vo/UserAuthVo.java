package com.litsynp.springsec.oauth.domain.auth.vo;

import com.litsynp.springsec.oauth.domain.member.domain.Member;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class UserAuthVo implements Serializable {

    private Long id;
    private String email;
    private String password;
    private Boolean active;

    public Long getId() {
        return id;
    }

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
                .id(member.getId())
                .email(member.getEmail())
                .password(member.getPassword())
                .active(member.getActive())
                .build();
    }
}
