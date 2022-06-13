package com.litsynp.springsecsession.domain.post.service;

import com.litsynp.springsecsession.domain.member.dao.MemberRepository;
import com.litsynp.springsecsession.domain.member.domain.Member;
import com.litsynp.springsecsession.domain.post.dao.PostRepository;
import com.litsynp.springsecsession.domain.post.domain.Post;
import com.litsynp.springsecsession.domain.post.dto.PostServiceCreateRequestDto;
import com.litsynp.springsecsession.domain.post.dto.PostServiceUpdateRequestDto;
import com.litsynp.springsecsession.domain.post.exception.PostMemberNotFoundException;
import com.litsynp.springsecsession.domain.post.exception.PostNotFoundException;
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

    @Transactional
    public Post create(PostServiceCreateRequestDto dto) {
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new PostMemberNotFoundException(dto.getMemberId()));

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
    public Post update(Long id, PostServiceUpdateRequestDto dto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));

        post.update(post.getMember(), dto.getTitle(), dto.getContent());

        return postRepository.save(post);
    }

    @Transactional
    public void deleteById(Long id) {
        Post found = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));

        postRepository.delete(found);
    }
}
