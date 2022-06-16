package com.litsynp.springsec.jwt.domain.auth.api;

import com.litsynp.springsec.jwt.domain.auth.dto.AuthMapper;
import com.litsynp.springsec.jwt.domain.auth.dto.TokenResponseDto;
import com.litsynp.springsec.jwt.domain.auth.dto.TokenCreateRequestDto;
import com.litsynp.springsec.jwt.domain.auth.dto.TokenValidateRequestDto;
import com.litsynp.springsec.jwt.domain.auth.dto.TokenValidateResponseDto;
import com.litsynp.springsec.jwt.domain.auth.vo.UserDetailsVo;
import com.litsynp.springsec.jwt.global.util.JwtUtil;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthApiController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final AuthMapper authMapper;

    @PostMapping("/tokens")
    public ResponseEntity<TokenResponseDto> login(@Valid @RequestBody TokenCreateRequestDto loginDto) {
        // Authenticate
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Create JWT token from authenticated information
        String jwtToken = jwtUtil.generateToken(authentication);

        UserDetailsVo userDetails = (UserDetailsVo) authentication.getPrincipal();
        TokenResponseDto response = authMapper.from(jwtToken, userDetails);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/tokens/refresh")
    public ResponseEntity<TokenResponseDto> refresh() {
        // TODO: Implement token refresh logic
        return ResponseEntity.ok(null);
    }

    @PostMapping("/tokens/validate")
    public ResponseEntity<TokenValidateResponseDto> validate(
            @Valid @RequestBody TokenValidateRequestDto dto) {
        boolean result = jwtUtil.validateToken(dto.getAccessToken());
        return ResponseEntity.ok(new TokenValidateResponseDto(result));
    }
}
