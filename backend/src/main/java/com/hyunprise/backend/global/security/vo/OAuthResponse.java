package com.hyunprise.backend.global.security.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OAuthResponse {
    private String email;
    private String name;
    private Integer gender;

    public static Integer parseGender(String gender) {
        return gender.equalsIgnoreCase("male") ? 0 : 1;
    }

    public static OAuthResponse fromKakao(KakaoResponse kakaoResponse) {
        if (kakaoResponse == null) return OAuthResponse.builder().build();
        return OAuthResponse.builder()
                .email(kakaoResponse.getKakaoAccount().getEmail())
                .name(kakaoResponse.getProperties().getNickname())
                .gender(parseGender(kakaoResponse.getKakaoAccount().getGender()))
                .build();
    }
}
