package com.litsynp.springsecsession.domain.auth.api;

import com.litsynp.springsecsession.domain.auth.dto.AuthMapper;
import com.litsynp.springsecsession.domain.auth.dto.AuthResponseDto;
import com.litsynp.springsecsession.domain.auth.dto.LoginRequestDto;
import com.litsynp.springsecsession.domain.auth.service.AuthService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthApiController {

    private final AuthService authService;
    private final AuthMapper authMapper;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(
            HttpServletRequest request,
            @RequestBody LoginRequestDto loginDto) {
        authService.login(request, authMapper.toServiceDto(loginDto));
        return ResponseEntity.ok(new AuthResponseDto(true));
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthResponseDto> logout(HttpServletRequest request) {
        authService.logout(request);
        return ResponseEntity.ok(new AuthResponseDto(true));
    }
}
