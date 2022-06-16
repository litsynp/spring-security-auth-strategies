package com.litsynp.springsec.jwt.domain.member.api;

import com.litsynp.springsec.jwt.domain.auth.service.AuthService;
import com.litsynp.springsec.jwt.domain.member.domain.Member;
import com.litsynp.springsec.jwt.domain.member.dto.MemberCreateRequestDto;
import com.litsynp.springsec.jwt.domain.member.dto.MemberMapper;
import com.litsynp.springsec.jwt.domain.member.dto.MemberResponseDto;
import com.litsynp.springsec.jwt.domain.member.service.MemberService;
import java.net.URI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/v1/members")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;
    private final AuthService authService;
    private final MemberMapper memberMapper;

    @PostMapping
    public ResponseEntity<MemberResponseDto> register(
            @Valid @RequestBody MemberCreateRequestDto memberDto) {
        Member registeredMember = memberService.register(memberMapper.toEntity(memberDto));
        MemberResponseDto response = memberMapper.toResponseDto(registeredMember);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}").buildAndExpand(registeredMember.getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDto> one(@PathVariable Long id) {
        Member member = memberService.findById(id);
        MemberResponseDto response = memberMapper.toResponseDto(member);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Member existing = memberService.findById(id);
        authService.checkAuthorization(existing.getId());
        memberService.delete(existing);

        return ResponseEntity.noContent().build();
    }
}
