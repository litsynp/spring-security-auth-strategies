package com.litsynp.springsec.session.domain.member.service;

import com.litsynp.springsec.session.domain.member.dao.MemberRepository;
import com.litsynp.springsec.session.domain.member.domain.Member;
import com.litsynp.springsec.session.domain.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member register(Member member) {
        member.changePassword(passwordEncoder.encode(member.getPassword()));
        return memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(id));
    }

    @Transactional
    public void delete(Member member) {
        memberRepository.delete(member);
    }
}
