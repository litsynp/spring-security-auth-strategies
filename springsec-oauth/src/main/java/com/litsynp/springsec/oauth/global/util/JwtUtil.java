package com.litsynp.springsec.oauth.global.util;

import com.litsynp.springsec.oauth.domain.auth.vo.UserDetailsVo;
import com.litsynp.springsec.oauth.global.config.AuthProperties;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtil {

    private final AuthProperties authProperties;

    public String generateToken(Authentication authentication) {
        UserDetailsVo userPrincipal = (UserDetailsVo) authentication.getPrincipal();
        Date now = new Date();
        return Jwts.builder()
                .setSubject(userPrincipal.getId().toString())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() +
                        authProperties.getAuth().getJwtAccessExpirationMs()))
                .signWith(SignatureAlgorithm.HS512, authProperties.getAuth().getJwtSecret())
                .compact();
    }

    public String generateTokenFromMemberId(Long memberId) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(memberId.toString())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() +
                        authProperties.getAuth().getJwtAccessExpirationMs()))
                .signWith(SignatureAlgorithm.HS512, authProperties.getAuth().getJwtSecret())
                .compact();
    }

    public String getMemberIdStrFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(authProperties.getAuth().getJwtSecret())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser()
                    .setSigningKey(authProperties.getAuth().getJwtSecret())
                    .parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
