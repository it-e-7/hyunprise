package com.hyunprise.backend.domain.auth.service;

import com.hyunprise.backend.domain.auth.vo.JwtToken;

public interface JwtTokenService {

    void saveJwtToken(JwtToken jwtToken);
}