package com.litsynp.springsec.oauth.domain.auth.dao;

import com.litsynp.springsec.oauth.domain.auth.domain.RefreshToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    int deleteByMemberId(Long memberId);

    int deleteByToken(String token);
}
