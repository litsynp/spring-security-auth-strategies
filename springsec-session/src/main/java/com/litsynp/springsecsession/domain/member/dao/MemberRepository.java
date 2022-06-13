package com.litsynp.springsecsession.domain.member.dao;

import com.litsynp.springsecsession.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
