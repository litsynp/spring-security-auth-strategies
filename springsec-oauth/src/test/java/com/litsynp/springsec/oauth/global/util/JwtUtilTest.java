package com.litsynp.springsec.oauth.global.util;

import static com.litsynp.springsec.oauth.config.SpringSecurityWebAuxTestConfig.getBasicMember;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.litsynp.springsec.oauth.config.SpringSecurityWebAuxTestConfig;
import com.litsynp.springsec.oauth.domain.auth.vo.UserDetailsVo;
import com.litsynp.springsec.oauth.global.config.AuthProperties;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import({JwtUtil.class, AuthProperties.class, SpringSecurityWebAuxTestConfig.class})
@EnableConfigurationProperties
@TestPropertySource(properties = {
        "app.auth.jwt-secret=foobar",
        "app.auth.jwt-access-expiration-ms=3600000"})
@ActiveProfiles({"jwt", "oauth"})
class JwtUtilTest {

    @Value("${app.auth.jwt-secret}")
    private String jwtSecret;

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    void generateToken_ok() {
        // given
        Authentication authentication = mock(Authentication.class);
        UserDetailsVo userDetails = UserDetailsVo.from(getBasicMember());
        given(authentication.getPrincipal()).willReturn(userDetails);

        // when
        String jwtToken = jwtUtil.generateToken(authentication);

        // then
        Long subject = Long.parseLong(Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(jwtToken)
                .getBody()
                .getSubject());

        assertThat(subject).isEqualTo(getBasicMember().getId());
    }

    @Test
    void generateTokenFromMemberId_ok() {
        // given
        Long memberId = getBasicMember().getId();

        // when
        String jwtToken = jwtUtil.generateTokenFromMemberId(memberId);

        // then
        Long subject = Long.parseLong(Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(jwtToken)
                .getBody()
                .getSubject());

        assertThat(subject).isEqualTo(memberId);
    }

    @Test
    void getMemberIdStrFromToken_ok() {
        // given
        Long memberId = getBasicMember().getId();
        String jwtToken = jwtUtil.generateTokenFromMemberId(memberId);

        // when
        Long memberIdFromToken = Long.parseLong(jwtUtil.getMemberIdStrFromToken(jwtToken));

        // then
        assertThat(memberIdFromToken).isEqualTo(memberId);
    }

    @Test
    @WithMockUser("testuser@example.com")
    void validateToken_ok() {
        // given
        Long memberId = getBasicMember().getId();
        String jwtToken = jwtUtil.generateTokenFromMemberId(memberId);

        // when
        boolean result = jwtUtil.validateToken(jwtToken);

        // then
        assertThat(result).isTrue();
    }
}
