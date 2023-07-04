package com.hyunprise.backend.global.security.filters;

import com.hyunprise.backend.global.security.managers.DummyAuthenticationManager;
import com.hyunprise.backend.global.security.utils.JwtUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthorizationFilter(JwtUtil jwtUtil) {
        super(new DummyAuthenticationManager());
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String token = jwtUtil.getTokenFromRequest(request);
        if (!jwtUtil.isValidToken(token)) {
            chain.doFilter(request, response);
            return;
        }

        String memberUUID = jwtUtil.getMemberUUIDFromToken(token);
        if (memberUUID == null) {
            chain.doFilter(request, response);
            return;
        }
        Authentication authentication = jwtUtil.jwtAuthenticationFromValidToken(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
    }
}
