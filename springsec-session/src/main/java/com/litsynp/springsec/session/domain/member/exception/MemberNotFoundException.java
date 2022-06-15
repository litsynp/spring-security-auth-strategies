package com.litsynp.springsec.session.domain.member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException(Long id) {
        super("No member with id = " + id);
    }
}
