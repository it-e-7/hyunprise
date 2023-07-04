package com.hyunprise.backend.global.security.types;

import java.util.Arrays;
import java.util.StringJoiner;

public enum OAuthProvider {
//    GOOGLE,
    KAKAO;

    public static String toListString() {
        StringJoiner joiner = new StringJoiner(", ", "[", "]");
        Arrays.stream(OAuthProvider.values())
                .map(Enum::name)
                .forEach(joiner::add);
        return joiner.toString();
    }
}
