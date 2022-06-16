package com.litsynp.springsec.jwt.domain.member.domain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleType {

    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN"),
    ;

    final String value;

    private static final Map<String, RoleType> roleTypeMap = new HashMap<>();

    static {
        for (RoleType role : RoleType.values()) {
            roleTypeMap.put(role.value, role);
        }
    }

    public static RoleType fromValue(String value) {
        RoleType type = roleTypeMap.get(value);
        if (type == null) {
            return USER;
        }
        return type;
    }
}
