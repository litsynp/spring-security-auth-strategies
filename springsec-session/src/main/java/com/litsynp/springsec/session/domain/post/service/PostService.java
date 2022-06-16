package com.litsynp.springsec.session.domain.post.service;

import com.litsynp.springsec.session.domain.auth.service.AuthService;
import com.litsynp.springsec.session.domain.member.dao.MemberRepository;
import com.litsynp.springsec.session.domain.member.domain.Member;
import com.litsynp.springsec.session.domain.member.exception.MemberIdNotFoundException;
import com.litsynp.springsec.session.domain.post.dao.PostRepository;
import com.litsynp.springsec.session.domain.post.domain.Post;
import com.litsynp.springsec.session.domain.post.dto.PostServiceCreateRequestDto;
import com.litsynp.springsec.session.domain.post.dto.PostServiceUpdateRequestDto;
import com.litsynp.springsec.session.domain.post.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final AuthService authService;

    @Transactional
    public Post create(PostServiceCreateRequestDto dto) {
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new MemberIdNotFoundException(dto.getMemberId()));

        Post post = Post.builder()
                .member(member)
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();

        return postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    @Transactional
    public Post update(Post post, PostServiceUpdateRequestDto dto) {
        post.update(post.getMember(), dto.getTitle(), dto.getContent());

        return postRepository.save(post);
    }

    @Transactional
    public void delete(Post post) {
        postRepository.delete(post);
    }
}
