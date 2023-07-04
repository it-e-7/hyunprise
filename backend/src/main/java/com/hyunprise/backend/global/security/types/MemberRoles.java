package com.hyunprise.backend.global.security.types;

import java.util.HashMap;
import java.util.Map;

public enum MemberRoles {
    MEMBER("MEMBER", 1),
    SELLER("SELLER", 10),
    DEV("DEV", 99);

    private static final Map<Integer, MemberRoles> BY_TYPE = new HashMap<>();
    static {
        for (MemberRoles r : values()) {
            BY_TYPE.put(r.type(), r);
        }
    }
    private final String role;
    private final Integer type;

    MemberRoles(String role, Integer type) {
        this.role = role;
        this.type = type;
    }

    public String role() { return role; }
    public Integer type() {
        return type;
    }

    public static MemberRoles roleOfType(Integer value) {
        return BY_TYPE.get(value);
    }
}
