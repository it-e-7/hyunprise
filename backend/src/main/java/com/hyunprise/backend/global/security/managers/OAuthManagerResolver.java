package com.hyunprise.backend.global.security.managers;

import com.hyunprise.backend.global.security.clients.KakaoClient;
import com.hyunprise.backend.domain.auth.vo.OAuth;
import com.hyunprise.backend.domain.member.services.MemberService;
import com.hyunprise.backend.global.security.types.OAuthProvider;
import com.hyunprise.backend.global.security.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuthManagerResolver implements AuthenticationManagerResolver<OAuth> {

    private final KakaoClient kakaoClient;
    private final JwtUtil jwtUtil;
    private final MemberService memberService;

    @Override
    public AuthenticationManager resolve(OAuth oAuth) {
        switch (oAuth.getProvider()) {
//            case GOOGLE:
//                return new GoogleAuthenticationManager();
            case KAKAO:
                return new KakaoAuthenticationManager(kakaoClient, jwtUtil, memberService);
            default:
                throw new IllegalArgumentException("Unknown provider " + oAuth.getProvider().name() + ". It must be one of " + OAuthProvider.toListString() );
        }
    }
}
