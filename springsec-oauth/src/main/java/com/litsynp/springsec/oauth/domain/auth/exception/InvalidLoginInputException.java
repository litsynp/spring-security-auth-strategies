package com.litsynp.springsec.oauth.domain.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidLoginInputException extends RuntimeException {

    public InvalidLoginInputException() {
        super("Invalid login input information");
    }
}
