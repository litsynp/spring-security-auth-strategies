package com.litsynp.springsec.jwt.domain.auth.domain;

import com.litsynp.springsec.jwt.domain.member.domain.Member;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "refresh_token_AK01", columnNames = {"token"})})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expireAt;

    @Builder
    public RefreshToken(Member member, String token, LocalDateTime expireAt) {
        this.member = member;
        this.token = token;
        this.expireAt = expireAt;
    }

    public boolean isExpired() {
        return getExpireAt().compareTo(LocalDateTime.now()) < 0;
    }
}
