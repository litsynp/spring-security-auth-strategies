package com.litsynp.springsecsession;

import com.litsynp.springsecsession.domain.member.domain.Member;
import com.litsynp.springsecsession.domain.member.dao.MemberRepository;
import com.litsynp.springsecsession.domain.member.domain.RoleType;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseInitializer {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.createMembers(10);
        initService.createAdmin();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final MemberRepository memberRepository;
        private final PasswordEncoder passwordEncoder;

        /**
         * Create members
         */
        public void createMembers(int num) {
            for (int i = 0; i < num; i++) {
                memberRepository.save(
                        new Member("testuser" + i + "@example.com",
                                passwordEncoder.encode("12345678")));
            }
        }

        /**
         * Create an admin member
         */
        public void createAdmin() {
            Member admin = memberRepository.save(
                    new Member("admin@example.com", passwordEncoder.encode("12345678"),
                            RoleType.ADMIN));

            log.info("Admin user ID: " + admin.getId() + ", Admin user email: " + admin.getEmail());
        }
    }
}
