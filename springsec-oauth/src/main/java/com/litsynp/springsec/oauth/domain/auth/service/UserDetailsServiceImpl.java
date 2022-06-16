package com.litsynp.springsec.oauth.domain.auth.service;

import com.litsynp.springsec.oauth.domain.auth.exception.UnauthorizedException;
import com.litsynp.springsec.oauth.domain.auth.vo.UserDetailsVo;
import com.litsynp.springsec.oauth.domain.member.dao.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetailsVo loadUserByUsername(String id) throws UsernameNotFoundException {
        return memberRepository.findByEmail(id).map(UserDetailsVo::from)
                .orElseThrow(UnauthorizedException::new);
    }
}
