package com.litsynp.springsec.oauth.domain.member.dao;

import com.litsynp.springsec.oauth.domain.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
}
