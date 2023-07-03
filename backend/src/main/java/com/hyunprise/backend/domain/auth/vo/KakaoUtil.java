package com.hyunprise.backend.domain.auth.vo;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class KakaoUtil {

    public KakaoResponse getUserInfo(String kakaoToken) throws JsonProcessingException {
//    public String getUserInfo(String kakaoToken) throws JsonProcessingException {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://kapi.kakao.com/v2/user/me")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + kakaoToken)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8")
                .build();
        return webClient
                .post()
                .retrieve()
                .bodyToMono(KakaoResponse.class)
                .block();

    }
}
