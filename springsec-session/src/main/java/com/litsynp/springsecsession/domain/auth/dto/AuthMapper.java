package com.litsynp.springsecsession.domain.auth.dto;

import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public LoginServiceRequestDto toServiceDto(LoginRequestDto dto) {
        return new LoginServiceRequestDto(dto.getEmail(), dto.getPassword());
    }
}
