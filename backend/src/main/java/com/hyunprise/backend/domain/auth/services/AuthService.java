package com.hyunprise.backend.domain.auth.services;

import com.hyunprise.backend.domain.auth.vo.OAuth;
import com.hyunprise.backend.domain.auth.vo.OAuthResult;
import org.springframework.security.core.AuthenticationException;

public interface AuthService {
    OAuthResult performOAuth (OAuth oAuth) throws AuthenticationException;
}
