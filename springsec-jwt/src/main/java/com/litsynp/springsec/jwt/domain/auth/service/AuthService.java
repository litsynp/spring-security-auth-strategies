package com.litsynp.springsec.jwt.domain.auth.service;

import com.litsynp.springsec.jwt.domain.auth.exception.UnauthorizedException;
import com.litsynp.springsec.jwt.domain.auth.vo.UserDetailsVo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public void checkAuthorization(Long resourceMemberId) {
        UserDetailsVo auth = (UserDetailsVo) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        if (!auth.getId().equals(resourceMemberId)) {
            throw new UnauthorizedException();
        }
    }
}
