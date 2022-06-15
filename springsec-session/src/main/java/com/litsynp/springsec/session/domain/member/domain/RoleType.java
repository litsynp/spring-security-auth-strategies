package com.litsynp.springsec.session.domain.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleType {

    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN"),
    ;

    final String value;
}
