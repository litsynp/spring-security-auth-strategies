package com.litsynp.springsec.session.domain.auth.session;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SessionCode {

    LOGIN_MEMBER("loginMember");

    final String value;
}
