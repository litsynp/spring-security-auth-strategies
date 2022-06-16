package com.litsynp.springsec.jwt.global.util;

import static com.litsynp.springsec.jwt.config.SpringSecurityWebAuxTestConfig.getBasicMember;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.litsynp.springsec.jwt.config.SpringSecurityWebAuxTestConfig;
import com.litsynp.springsec.jwt.domain.auth.vo.UserDetailsVo;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import({JwtUtil.class, SpringSecurityWebAuxTestConfig.class})
@TestPropertySource(properties = {
        "app.jwt-secret=3600000",
        "app.jwt-access-expiration-ms=86400000"})
class JwtUtilTest {

    @Value("${app.jwt-secret}")
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
        String subject = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(jwtToken)
                .getBody()
                .getSubject();

        assertThat(subject).isEqualTo(getBasicMember().getEmail());
    }

    @Test
    void generateTokenFromEmail_ok() {
        // given
        String email = getBasicMember().getEmail();

        // when
        String jwtToken = jwtUtil.generateTokenFromEmail(email);

        // then
        String subject = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(jwtToken)
                .getBody()
                .getSubject();

        assertThat(subject).isEqualTo(getBasicMember().getEmail());
    }

    @Test
    void getEmailFromToken_ok() {
        // given
        String email = getBasicMember().getEmail();
        String jwtToken = jwtUtil.generateTokenFromEmail(email);

        // when
        String emailFromToken = jwtUtil.getEmailFromToken(jwtToken);

        // then
        assertThat(emailFromToken).isEqualTo(email);
    }

    @Test
    @WithMockUser("testuser@example.com")
    void validateToken_ok() {
        // given
        String email = getBasicMember().getEmail();
        String jwtToken = jwtUtil.generateTokenFromEmail(email);

        // when
        boolean result = jwtUtil.validateToken(jwtToken);

        // then
        assertThat(result).isTrue();
    }
}
