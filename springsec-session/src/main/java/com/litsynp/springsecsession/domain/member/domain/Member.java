package com.litsynp.springsecsession.domain.member.domain;

import com.litsynp.springsecsession.global.domain.domain.BaseTimeEntity;
import javax.persistence.Entity;
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

    public void changePassword(String password) {
        this.password = password;
    }

    @Builder
    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
