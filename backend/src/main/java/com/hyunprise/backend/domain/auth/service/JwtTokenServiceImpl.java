package com.hyunprise.backend.domain.auth.service;

import com.hyunprise.backend.domain.auth.dao.AuthMapper;
import com.hyunprise.backend.domain.auth.vo.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtTokenServiceImpl implements JwtTokenService{

    private final AuthMapper authMapper;

    @Override
    public void saveJwtToken(JwtToken jwtToken) {
        boolean isSaved = authMapper.insertToken(jwtToken) == 1;
        if (!isSaved) {
            throw new StringIndexOutOfBoundsException();
        }
    }

}
