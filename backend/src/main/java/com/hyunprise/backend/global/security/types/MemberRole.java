package com.hyunprise.backend.global.security.types;

import java.util.HashMap;
import java.util.Map;

public enum MemberRole {
    MEMBER("MEMBER", 1),
    SELLER("SELLER", 10),
    DEV("DEV", 99);

    private static final Map<Integer, MemberRole> BY_TYPE = new HashMap<>();
    static {
        for (MemberRole r : values()) {
            BY_TYPE.put(r.type(), r);
        }
    }
    private final String role;
    private final Integer type;

    MemberRole(String role, Integer type) {
        this.role = role;
        this.type = type;
    }

    public String role() { return role; }
    public Integer type() {
        return type;
    }

    public static MemberRole roleOfType(Integer value) {
        return BY_TYPE.get(value);
    }
}
