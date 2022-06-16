package com.litsynp.springsec.jwt.domain.auth.vo;

import com.litsynp.springsec.jwt.domain.member.domain.Member;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@RequiredArgsConstructor
public class UserDetailsVo implements UserDetails {

    @Delegate
    private final UserAuthVo userVo;

    private final Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsVo from(Member member) {
        UserAuthVo user = UserAuthVo.fromEntity(member);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(
                new SimpleGrantedAuthority(member.getRole().getValue()));
        return new UserDetailsVo(user, authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return userVo.getVoPassword();
    }

    @Override
    public String getUsername() {
        return userVo.getVoEmail();
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
