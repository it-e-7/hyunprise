package com.hyunprise.backend.domain.auth.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KakaoResponse {

    private long id;
    @JsonProperty("properties")
    private Properties properties;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;


    public static class Properties {

        @JsonProperty("nickname")
        private String nickname;

        public String getNickname() {
            return nickname;
        }

        @Override
        public String toString() {
            return "Properties{" +
                    "nickname='" + nickname + '\'' +
                    '}';
        }
    }


    public static class KakaoAccount {

        @JsonProperty("email")
        private String email;

        @JsonProperty("gender")
        private String gender;

        public String getEmail() {
            return email;
        }

        public String getGender() {
            return gender;
        }

        @Override
        public String toString() {
            return "KakaoAccount{" +
                    ", email='" + email + '\'' +
                    ", gender='" + gender + '\'' +
                    '}';
        }
    }

    public long getId() {
        return id;
    }

    public Properties getProperties() {
        return properties;
    }

    public KakaoAccount getKakaoAccount() {
        return kakaoAccount;
    }

    @Override
    public String toString() {
        return "KakaoResponse{" +
                "id=" + id +
                ", properties=" + properties +
                ", kakaoAccount=" + kakaoAccount +
                '}';
    }
}