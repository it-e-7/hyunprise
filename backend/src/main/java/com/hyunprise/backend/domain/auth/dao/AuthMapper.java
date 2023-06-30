package com.hyunprise.backend.domain.auth.dao;

import com.hyunprise.backend.domain.auth.vo.JwtToken;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthMapper {
    int insertToken(JwtToken jwtToken);
}
