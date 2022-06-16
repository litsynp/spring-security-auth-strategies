package com.litsynp.springsec.oauth.domain.member.domain;

import com.litsynp.springsec.oauth.global.domain.domain.BaseTimeEntity;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String email;

    private String password;

    private Boolean active;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    public void changePassword(String password) {
        this.password = password;
    }

    @Builder
    public Member(String email, String password) {
        this.email = email;
        this.password = password;
        this.active = true;
        this.role = RoleType.USER;
    }

    @Builder
    public Member(String email, String password, RoleType role) {
        this.email = email;
        this.password = password;
        this.active = true;
        this.role = role;
    }
}
