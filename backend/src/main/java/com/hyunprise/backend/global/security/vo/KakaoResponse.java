package com.hyunprise.backend.global.security.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class KakaoResponse {

    private long id;
    @JsonProperty("properties")
    private Properties properties;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;


    @Getter
    @ToString
    public static class Properties {

        @JsonProperty("nickname")
        private String nickname;

    }

    @Getter
    @ToString
    public static class KakaoAccount {

        @JsonProperty("email")
        private String email;

        @JsonProperty("gender")
        private String gender;

    }
}