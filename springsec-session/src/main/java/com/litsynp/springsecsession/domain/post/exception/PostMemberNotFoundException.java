package com.litsynp.springsecsession.domain.post.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class PostMemberNotFoundException extends RuntimeException {

    public PostMemberNotFoundException(Long id) {
        super("No member with id = " + id);
    }
}
