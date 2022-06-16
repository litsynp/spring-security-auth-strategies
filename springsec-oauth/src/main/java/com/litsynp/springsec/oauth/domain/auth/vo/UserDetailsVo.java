package com.litsynp.springsec.oauth.domain.auth.vo;

import com.litsynp.springsec.oauth.domain.member.domain.Member;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class UserDetailsVo implements UserDetails, OAuth2User, OidcUser {

    @Delegate
    private final UserAuthVo userVo;

    private final Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public static UserDetailsVo from(Member member) {
        UserAuthVo user = UserAuthVo.fromEntity(member);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(
                new SimpleGrantedAuthority(member.getRole().getValue()));
        return new UserDetailsVo(user, authorities);
    }

    public static UserDetailsVo from(Member member, Map<String, Object> attributes) {
        UserAuthVo user = UserAuthVo.fromEntity(member);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(
                new SimpleGrantedAuthority(member.getRole().getValue()));
        return new UserDetailsVo(user, authorities, attributes);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return userVo.getId().toString();
    }

    @Override
    public String getName() {
        return userVo.getId().toString();
    }

    @Override
    public String getPassword() {
        return userVo.getVoPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return userVo.getVoActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return userVo.getVoActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return userVo.getVoActive();
    }

    @Override
    public boolean isEnabled() {
        return userVo.getVoActive();
    }

    @Override
    public Map<String, Object> getClaims() {
        return null;
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return null;
    }

    @Override
    public OidcIdToken getIdToken() {
        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserDetailsVo user = (UserDetailsVo) o;
        return Objects.equals(this.userVo.getId(), user.getId());
    }
}
