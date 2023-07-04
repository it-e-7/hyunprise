package com.hyunprise.backend.global.security.filters;

import com.hyunprise.backend.global.security.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtFilterFactory {
    private final JwtUtil jwtUtil;

    public BasicAuthenticationFilter testAuthorization() {
        return new JwtAuthorizationFilter(jwtUtil);
    }
}
