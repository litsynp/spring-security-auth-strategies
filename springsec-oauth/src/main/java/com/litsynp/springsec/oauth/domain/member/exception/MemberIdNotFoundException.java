package com.litsynp.springsec.oauth.domain.member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class MemberIdNotFoundException extends RuntimeException {

    public MemberIdNotFoundException(Long id) {
        super("No member with id = " + id);
    }
}
