package com.litsynp.springsec.jwt.domain.auth.api;

import com.litsynp.springsec.jwt.domain.auth.domain.RefreshToken;
import com.litsynp.springsec.jwt.domain.auth.dto.AccessTokenRefreshRequestDto;
import com.litsynp.springsec.jwt.domain.auth.dto.AccessTokenValidateRequestDto;
import com.litsynp.springsec.jwt.domain.auth.dto.AccessTokenValidateResponseDto;
import com.litsynp.springsec.jwt.domain.auth.dto.AuthMapper;
import com.litsynp.springsec.jwt.domain.auth.dto.RefreshTokenAllInvalidateRequestDto;
import com.litsynp.springsec.jwt.domain.auth.dto.RefreshTokenInvalidateRequestDto;
import com.litsynp.springsec.jwt.domain.auth.dto.TokenCreateRequestDto;
import com.litsynp.springsec.jwt.domain.auth.dto.TokenResponseDto;
import com.litsynp.springsec.jwt.domain.auth.service.AuthService;
import com.litsynp.springsec.jwt.domain.auth.service.RefreshTokenService;
import com.litsynp.springsec.jwt.domain.auth.vo.UserDetailsVo;
import com.litsynp.springsec.jwt.domain.member.domain.Member;
import com.litsynp.springsec.jwt.global.util.JwtUtil;
import java.net.URI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthApiController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final AuthMapper authMapper;
    private final RefreshTokenService refreshTokenService;
    private final AuthService authService;

    /**
     * Create access and refresh token
     */
    @PostMapping("/tokens")
    public ResponseEntity<TokenResponseDto> createTokens(
            @Valid @RequestBody TokenCreateRequestDto dto) {
        // Authenticate
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getEmail(), dto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Create JWT access token from authenticated information
        String accessToken = jwtUtil.generateToken(authentication);

        // Get authentication details
        UserDetailsVo userDetails = (UserDetailsVo) authentication.getPrincipal();

        // Create refresh token and save to database
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        TokenResponseDto response = authMapper.from(accessToken, refreshToken.getToken(),
                userDetails);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().build()
                .toUri();

        return ResponseEntity.created(location)
                .body(response);
    }

    /**
     * Validate access token
     */
    @PostMapping("/tokens/validate")
    public ResponseEntity<AccessTokenValidateResponseDto> validateAccessToken(
            @Valid @RequestBody AccessTokenValidateRequestDto dto) {
        boolean result = jwtUtil.validateToken(dto.getAccessToken());
        return ResponseEntity.ok(new AccessTokenValidateResponseDto(result));
    }

    /**
     * Refresh access token with valid refresh token
     */
    @PostMapping("/tokens/refresh")
    public ResponseEntity<TokenResponseDto> refreshAccessToken(
            @Valid @RequestBody AccessTokenRefreshRequestDto dto) {
        // Validate refresh token by looking for it from the database
        String refreshTokenStr = dto.getRefreshToken();
        RefreshToken refreshToken = refreshTokenService.findByToken(refreshTokenStr);
        // If it exists yet is expired, remove from database and throw exception
        refreshTokenService.verifyExpiration(refreshToken);

        // Create access token
        Member member = refreshToken.getMember();
        String accessToken = jwtUtil.generateTokenFromEmail(member.getEmail());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().build()
                .toUri();

        return ResponseEntity.created(location)
                .body(authMapper.from(accessToken, refreshToken.getToken(), member));
    }

    /**
     * Invalidate and delete given refresh token if it exists
     */
    @DeleteMapping("/refresh-tokens")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Void> invalidateRefreshToken(
            @Valid @RequestBody RefreshTokenInvalidateRequestDto dto) {
        authService.checkAuthorization(dto.getMemberId());
        refreshTokenService.deleteByToken(dto.getRefreshToken());
        return ResponseEntity.noContent().build();
    }

    /**
     * Invalidate and delete all existing refresh tokens of member
     */
    @DeleteMapping("/refresh-tokens/all")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Void> invalidateAllRefreshTokensOfMember(
            @Valid @RequestBody RefreshTokenAllInvalidateRequestDto dto) {
        authService.checkAuthorization(dto.getMemberId());
        refreshTokenService.deleteByMemberId(dto.getMemberId());
        return ResponseEntity.noContent().build();
    }
}
