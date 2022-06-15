package com.litsynp.springsec.session.domain.auth.vo;

import com.litsynp.springsec.session.domain.member.domain.Member;
import java.util.Collection;
import java.util.Collections;
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

    public UserDetailsVo(Member member) {
        this.userVo = UserAuthVo.fromEntity(member);
        this.authorities = Collections.singleton(
                new SimpleGrantedAuthority(member.getRole().getValue()));
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
}
