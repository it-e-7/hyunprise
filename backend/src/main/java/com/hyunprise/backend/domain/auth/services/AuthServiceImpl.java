package com.hyunprise.backend.domain.auth.services;

import com.hyunprise.backend.domain.auth.vo.OAuthResult;
import com.hyunprise.backend.domain.auth.vo.OAuth;
import com.hyunprise.backend.global.security.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwtUtil;
    private final AuthenticationManagerResolver<OAuth> authenticationManagerResolver;

    @Override
    public OAuthResult performOAuth (OAuth oAuth) throws AuthenticationException {
        AuthenticationManager manager = authenticationManagerResolver.resolve(oAuth);
        Authentication authenticationToken = jwtUtil.authenticationFromOAuth(oAuth);

        Authentication authentication = manager.authenticate(authenticationToken);
        String token = jwtUtil.getTokenFromAuthentication(authentication);

        return OAuthResult.builder()
                .successful(true)
                .accessToken(token)
                .build();
    }
}
