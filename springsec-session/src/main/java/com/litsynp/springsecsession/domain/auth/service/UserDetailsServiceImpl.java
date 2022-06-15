package com.litsynp.springsecsession.domain.auth.service;

import com.litsynp.springsecsession.domain.auth.exception.UnauthorizedException;
import com.litsynp.springsecsession.domain.auth.vo.UserDetailsVo;
import com.litsynp.springsecsession.domain.member.dao.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetailsVo loadUserByUsername(String email) throws UsernameNotFoundException {
        return memberRepository.findByEmail(email).map(UserDetailsVo::new)
                .orElseThrow(UnauthorizedException::new);
    }
}
