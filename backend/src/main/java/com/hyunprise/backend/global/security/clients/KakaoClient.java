package com.hyunprise.backend.global.security.clients;

import com.hyunprise.backend.global.security.vo.KakaoResponse;
import com.hyunprise.backend.global.security.vo.OAuthResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class KakaoClient {

    private final String baseUrl;

    public KakaoClient(@Value("${spring.security.oauth2.client.provider.kakao}") String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public OAuthResponse getUserInfo(String kakaoToken) {
        WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + kakaoToken)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8")
                .build();
        try {
            KakaoResponse kakaoResponse = webClient
                    .post()
                    .retrieve()
                    .bodyToMono(KakaoResponse.class)
                    .block();
            return OAuthResponse.fromKakao(kakaoResponse);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }
}
