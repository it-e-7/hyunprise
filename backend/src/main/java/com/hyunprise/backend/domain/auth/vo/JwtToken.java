package com.hyunprise.backend.domain.auth.vo;
public class JwtToken {

    private long id;
    private String jwtToken;

    public JwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }
}