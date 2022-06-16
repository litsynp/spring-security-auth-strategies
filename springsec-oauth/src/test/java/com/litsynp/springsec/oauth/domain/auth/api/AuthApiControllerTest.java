package com.litsynp.springsec.oauth.domain.auth.api;

import static com.litsynp.springsec.oauth.config.SpringSecurityWebAuxTestConfig.getBasicMember;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.litsynp.springsec.oauth.domain.auth.domain.RefreshToken;
import com.litsynp.springsec.oauth.domain.auth.dto.AccessTokenRefreshRequestDto;
import com.litsynp.springsec.oauth.domain.auth.dto.AccessTokenValidateRequestDto;
import com.litsynp.springsec.oauth.domain.auth.dto.AccessTokenValidateResponseDto;
import com.litsynp.springsec.oauth.domain.auth.dto.AuthMapper;
import com.litsynp.springsec.oauth.domain.auth.dto.RefreshTokenAllInvalidateRequestDto;
import com.litsynp.springsec.oauth.domain.auth.dto.RefreshTokenInvalidateRequestDto;
import com.litsynp.springsec.oauth.domain.auth.dto.TokenCreateRequestDto;
import com.litsynp.springsec.oauth.domain.auth.dto.TokenResponseDto;
import com.litsynp.springsec.oauth.domain.auth.service.RefreshTokenService;
import com.litsynp.springsec.oauth.domain.auth.vo.UserDetailsVo;
import com.litsynp.springsec.oauth.domain.member.domain.Member;
import com.litsynp.springsec.oauth.global.util.JwtUtil;
import com.litsynp.springsec.oauth.template.ApiMockControllerTest;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;

@WebMvcTest(AuthApiController.class)
@Import({AuthMapper.class})
class AuthApiControllerTest extends ApiMockControllerTest {

    @Mock
    private Authentication authentication;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private RefreshTokenService refreshTokenService;

    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    private AuthMapper authMapper;

    @Value("${app.auth.jwt-secret}")
    private String jwtSecret;

    @Value("${app.auth.jwt-access-expiration-ms}")
    private int jwtAccessExpirationMs;

    @Value("${app.auth.jwt-refresh-expiration-ms}")
    private int jwtRefreshExpirationMs;

    @Test
    @DisplayName("Create tokens - Ok")
    void createTokens_ok() throws Exception {
        // given
        Member member = getBasicMember();
        TokenCreateRequestDto requestDto = new TokenCreateRequestDto(getBasicMember().getEmail(),
                getBasicMember().getPassword());

        String accessToken = "aaa.bbb.ccc";
        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .member(member)
                .expireAt(LocalDateTime.now().plusSeconds(jwtRefreshExpirationMs / 1000))
                .build();

        TokenResponseDto responseDto = authMapper.from(accessToken, refreshToken.getToken(),
                member);

        given(authenticationManager.authenticate(any()))
                .willReturn(authentication);

        given(jwtUtil.generateToken(any()))
                .willReturn(accessToken);

        given(authentication.getPrincipal())
                .willReturn(UserDetailsVo.from(member));

        given(refreshTokenService.createRefreshToken(any()))
                .willReturn(refreshToken);

        // when & then
        mockMvc.perform(post("/v1/auth/tokens")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(responseDto)));
    }

    @Test
    @DisplayName("Validate access token - Ok")
    void validateAccessToken_ok() throws Exception {
        // given
        String accessToken = "aaa.bbb.ccc";
        AccessTokenValidateRequestDto requestDto = new AccessTokenValidateRequestDto(accessToken);
        AccessTokenValidateResponseDto responseDto = new AccessTokenValidateResponseDto(true);

        given(jwtUtil.validateToken(accessToken))
                .willReturn(true);

        // when & then
        mockMvc.perform(post("/v1/auth/tokens/validate")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(responseDto)));
    }

    @Test
    @DisplayName("Refresh access token - Ok")
    void refreshAccessToken_ok() throws Exception {
        // given
        Member member = getBasicMember();
        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .member(member)
                .expireAt(LocalDateTime.now().plusSeconds(jwtRefreshExpirationMs / 1000))
                .build();

        AccessTokenRefreshRequestDto requestDto = new AccessTokenRefreshRequestDto(
                refreshToken.getToken());

        String accessToken = "aaa.bbb.ccc";
        TokenResponseDto responseDto = authMapper.from(accessToken, refreshToken.getToken(),
                member);

        given(jwtUtil.generateTokenFromMemberId(member.getId()))
                .willReturn(accessToken);

        given(refreshTokenService.findByToken(refreshToken.getToken()))
                .willReturn(refreshToken);

        // when & then
        mockMvc.perform(post("/v1/auth/tokens/refresh")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(responseDto)));
    }

    @Test
    @DisplayName("Invalidate refresh token - Ok")
    @WithMockUser("testuser@example.com")
    void invalidateRefreshTokens_ok() throws Exception {
        // given
        Member member = getBasicMember();
        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .member(member)
                .expireAt(LocalDateTime.now().plusSeconds(jwtRefreshExpirationMs / 1000))
                .build();

        RefreshTokenInvalidateRequestDto requestDto = new RefreshTokenInvalidateRequestDto(
                member.getId(), refreshToken.getToken());

        // when & then
        mockMvc.perform(delete("/v1/auth/refresh-tokens")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Invalidate all refresh tokens - Ok")
    @WithMockUser("testuser@example.com")
    void invalidateAllRefreshTokensOfMember_ok() throws Exception {
        // given
        RefreshTokenAllInvalidateRequestDto requestDto = new RefreshTokenAllInvalidateRequestDto(
                getBasicMember().getId());

        // when & then
        mockMvc.perform(delete("/v1/auth/refresh-tokens/all")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
