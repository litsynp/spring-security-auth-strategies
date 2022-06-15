package com.litsynp.springsec.session;

import com.litsynp.springsec.session.domain.member.domain.Member;
import com.litsynp.springsec.session.domain.member.dao.MemberRepository;
import com.litsynp.springsec.session.domain.member.domain.RoleType;
import com.litsynp.springsec.session.domain.post.dao.PostRepository;
import com.litsynp.springsec.session.domain.post.domain.Post;
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
        initService.createMembersAndPosts(10);
        initService.createAdmin();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final MemberRepository memberRepository;
        private final PostRepository postRepository;
        private final PasswordEncoder passwordEncoder;

        /**
         * Create members
         */
        public void createMembersAndPosts(int num) {
            for (int i = 1; i <= num; i++) {
                Member member = memberRepository.save(
                        new Member("testuser" + i + "@example.com",
                                passwordEncoder.encode("12345678")));

                createPost(member);
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

        /**
         * Create a post by member
         */
        public void createPost(Member member) {
            postRepository.save(new Post(member,
                    "Test title by " + member.getEmail() + "(" + +member.getId() + ")",
                    "Test content"));
        }
    }
}
