package com.litsynp.springsec.oauth.global.error.exception;

import com.litsynp.springsec.oauth.domain.oauth.domain.ProviderType;
import org.springframework.security.core.AuthenticationException;

public class OAuth2ProviderMismatchException extends AuthenticationException {

    public OAuth2ProviderMismatchException(ProviderType attemptedProviderType,
            ProviderType savedProviderType) {
        super("Looks like you're signed up with " + attemptedProviderType
                + " account. Please use your " + savedProviderType
                + " account to login.");
    }
}
