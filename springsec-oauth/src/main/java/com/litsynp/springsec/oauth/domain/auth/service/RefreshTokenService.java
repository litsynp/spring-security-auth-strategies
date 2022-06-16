package com.litsynp.springsec.oauth.domain.auth.service;

import com.litsynp.springsec.oauth.domain.auth.dao.RefreshTokenRepository;
import com.litsynp.springsec.oauth.domain.auth.domain.RefreshToken;
import com.litsynp.springsec.oauth.domain.auth.exception.RefreshTokenExpiredException;
import com.litsynp.springsec.oauth.domain.auth.exception.RefreshTokenInvalidException;
import com.litsynp.springsec.oauth.domain.member.dao.MemberRepository;
import com.litsynp.springsec.oauth.domain.member.exception.MemberIdNotFoundException;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;

    @Value("${app.jwt-refresh-expiration-ms}")
    private Long refreshTokenExpirationMs;

    @Transactional
    public RefreshToken createRefreshToken(Long memberId) {
        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .member(memberRepository.findById(memberId)
                        .orElseThrow(() -> new MemberIdNotFoundException(memberId)))
                .expireAt(LocalDateTime.now().plusSeconds(refreshTokenExpirationMs / 1000))
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    @Transactional(readOnly = true)
    public RefreshToken findByToken(String refreshTokenStr) {
        return refreshTokenRepository.findByToken(refreshTokenStr)
                .orElseThrow(RefreshTokenInvalidException::new);
    }

    @Transactional(readOnly = true)
    public void verifyExpiration(RefreshToken refreshToken) {
        log.info(refreshToken.getExpireAt().toString());
        log.info(LocalDateTime.now().toString());
        if (refreshToken.isExpired()) {
            refreshTokenRepository.delete(refreshToken);
            throw new RefreshTokenExpiredException();
        }
    }

    @Transactional
    public int deleteByToken(String refreshToken) {
        return refreshTokenRepository.deleteByToken(refreshToken);
    }

    @Transactional
    public int deleteByMemberId(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new MemberIdNotFoundException(memberId);
        }
        return refreshTokenRepository.deleteByMemberId(memberId);
    }
}