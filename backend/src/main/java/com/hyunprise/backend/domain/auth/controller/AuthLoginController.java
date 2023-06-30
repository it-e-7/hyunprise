package com.hyunprise.backend.domain.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hyunprise.backend.domain.auth.service.JwtTokenService;
import com.hyunprise.backend.domain.auth.utils.AuthToken;
import com.hyunprise.backend.domain.auth.utils.AuthTokenProvider;
import com.hyunprise.backend.domain.auth.vo.KakaoUtil;
import com.hyunprise.backend.domain.auth.vo.JwtToken;
import com.hyunprise.backend.domain.auth.vo.KakaoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class
AuthLoginController {

    private final KakaoUtil kakaoUtil;
    private final JwtTokenService jwtTokenService;
    private final AuthTokenProvider authTokenProvider;


    public AuthLoginController(KakaoUtil kakaoUtil, JwtTokenService jwtTokenService,
                               AuthTokenProvider authTokenProvider) {
        this.kakaoUtil = kakaoUtil;
        this.jwtTokenService = jwtTokenService;
        this.authTokenProvider = authTokenProvider;
    }

    @PostMapping("/v1/oauth/authorize")
    public ResponseEntity<JwtToken> getMemberInfo(@RequestParam String token) throws JsonProcessingException {

        KakaoResponse kakaoResponse = kakaoUtil.getUserInfo(token);
        AuthToken appToken = authTokenProvider.createUserAppToken(Long.toString(kakaoResponse.getId()));

        return null;
    }



}
