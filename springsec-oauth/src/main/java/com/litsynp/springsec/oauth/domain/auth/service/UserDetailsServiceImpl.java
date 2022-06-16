package com.litsynp.springsec.oauth.domain.auth.service;

import com.litsynp.springsec.oauth.domain.auth.exception.UnauthorizedException;
import com.litsynp.springsec.oauth.domain.auth.vo.UserDetailsVo;
import com.litsynp.springsec.oauth.domain.member.dao.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetailsVo loadUserByUsername(String email) throws UsernameNotFoundException {
        return memberRepository.findByEmail(email).map(UserDetailsVo::from)
                .orElseThrow(UnauthorizedException::new);
    }

    public UserDetailsVo loadUserByMemberId(Long memberId) throws UsernameNotFoundException {
        return memberRepository.findById(memberId).map(UserDetailsVo::from)
                .orElseThrow(UnauthorizedException::new);
    }
}
