package com.hyunprise.backend.global.security.managers;

import com.hyunprise.backend.global.security.clients.KakaoClient;
import com.hyunprise.backend.global.security.vo.OAuthResponse;
import com.hyunprise.backend.domain.member.services.MemberService;
import com.hyunprise.backend.domain.member.vo.Member;
import com.hyunprise.backend.global.security.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@RequiredArgsConstructor
public class KakaoAuthenticationManager implements AuthenticationManager {

    private final KakaoClient kakaoClient;
    private final JwtUtil jwtUtil;
    private final MemberService memberService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String kakaoToken = jwtUtil.getTokenFromAuthentication(authentication);

        OAuthResponse response = kakaoClient.getUserInfo(kakaoToken);
        if (response == null) {
            throw new BadCredentialsException("Invalid Kakao Access Token");
        }
        // 처음 로그인 시 카카오 데이터 불러오기 및 DB에 삽입
        Member member = Member.fromOAuthResponse(response);
        memberService.upsertOneMember(member);
        
        // 토큰 생성
        String token = jwtUtil.jwtTokenOfMember(member);
        return jwtUtil.jwtAuthenticationFromValidToken(token);
    }
}
