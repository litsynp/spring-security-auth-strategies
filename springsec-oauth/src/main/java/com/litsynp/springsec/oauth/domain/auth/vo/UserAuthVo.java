package com.litsynp.springsec.oauth.domain.auth.vo;

import com.litsynp.springsec.oauth.domain.member.domain.Member;
import com.litsynp.springsec.oauth.domain.member.domain.RoleType;
import com.litsynp.springsec.oauth.domain.oauth.domain.ProviderType;
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
    private String oauthId;
    private ProviderType providerType;
    private RoleType roleType;

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

    public String getVoOauthId() {
        return oauthId;
    }

    public ProviderType getVoProviderType() {
        return providerType;
    }

    public RoleType getVoRoleType() {
        return roleType;
    }

    public static UserAuthVo fromEntity(Member member) {
        return UserAuthVo.builder()
                .id(member.getId())
                .email(member.getEmail())
                .password(member.getPassword())
                .active(member.getActive())
                .oauthId(member.getOauthId())
                .providerType(member.getProviderType())
                .roleType(member.getRole())
                .build();
    }
}
