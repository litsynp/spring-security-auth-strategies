package com.litsynp.springsec.jwt.domain.member.dao;

import com.litsynp.springsec.jwt.domain.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
}
