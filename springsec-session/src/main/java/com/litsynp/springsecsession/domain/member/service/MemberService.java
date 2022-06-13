package com.litsynp.springsecsession.domain.member.service;

import com.litsynp.springsecsession.domain.member.dao.MemberRepository;
import com.litsynp.springsecsession.domain.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No member with id = " + id));
    }

    @Transactional
    public Member register(Member member) {
        return memberRepository.save(member);
    }

    @Transactional
    public void deleteById(Long id) {
        Member found = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No member with id = " + id));
        memberRepository.delete(found);
    }
}
