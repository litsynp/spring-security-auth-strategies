package com.litsynp.springsec.oauth.domain.member.domain;

import com.litsynp.springsec.oauth.domain.oauth.domain.OAuth2UserInfo;
import com.litsynp.springsec.oauth.domain.oauth.domain.ProviderType;
import com.litsynp.springsec.oauth.global.domain.domain.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(name = "member_AK01", columnNames = {"providerType", "oauthId"})})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Email
    @Column(nullable = false)
    private String email;

    private String password;

    @Column(nullable = false, length = 100)
    @Setter
    private String username;

    @NotNull
    private Boolean active;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProviderType providerType;

    private String oauthId;

    @Column(length = 512)
    @Setter
    private String profileImageUrl;

    public void changePassword(String password) {
        this.password = password;
    }

    public void update(OAuth2UserInfo userInfo) {
        // Set name of the user from OAuth provider if updated
        if (userInfo.getName() != null && !this.username.equals(userInfo.getName())) {
            this.username = userInfo.getName();
        }

        // Set user profile image from OAuth provider if updated
        if (userInfo.getImageUrl() != null &&
            !this.profileImageUrl.equals(userInfo.getImageUrl())) {
            this.profileImageUrl = userInfo.getImageUrl();
        }
    }

    public Member(String email, String password, RoleType role) {
        this.email = email;
        this.password = password;
        this.username = "";
        this.active = true;
        this.role = role;
        this.providerType = ProviderType.LOCAL;
        this.oauthId = null;
        this.profileImageUrl = null;
    }

    public Member(String email, String password) {
        this(email, password, RoleType.USER);
    }

    public Member(OAuth2UserInfo userInfo, ProviderType providerType) {
        this.email = userInfo.getEmail();
        this.password = "";
        this.username = userInfo.getName();
        this.active = true;
        this.role = RoleType.USER;
        this.providerType = providerType;
        this.oauthId = userInfo.getId();
        this.profileImageUrl = userInfo.getImageUrl();
    }
}
