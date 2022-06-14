package com.litsynp.springsecsession.domain.auth.service;

import com.litsynp.springsecsession.domain.auth.dto.LoginServiceRequestDto;
import com.litsynp.springsecsession.domain.auth.exception.UnauthorizedException;
import com.litsynp.springsecsession.domain.auth.session.SessionCode;
import com.litsynp.springsecsession.domain.auth.vo.MemberSessionVo;
import com.litsynp.springsecsession.domain.member.dao.MemberRepository;
import com.litsynp.springsecsession.domain.member.domain.Member;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public void login(HttpServletRequest request, LoginServiceRequestDto dto) {
        Member member = memberRepository.findByEmail(dto.getEmail())
                .orElseThrow(UnauthorizedException::new);

        if (!passwordEncoder.matches(dto.getPassword(), member.getPassword())) {
            throw new UnauthorizedException();
        }

        // Save logged-in user info in session
        HttpSession session = request.getSession();
        MemberSessionVo sessionVo = new MemberSessionVo(member.getId());
        session.setAttribute(SessionCode.LOGIN_MEMBER.getValue(), sessionVo);
    }

    public void logout(HttpServletRequest request) {
        // getSession(false) so that it returns null
        // instead of creating a new session when the session doesn't exist
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
