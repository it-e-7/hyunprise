package com.hyunprise.backend.domain.auth.vo;

import com.hyunprise.backend.global.security.types.OAuthProvider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OAuth {
    private String memberUUID;
    private OAuthProvider provider;
    private String authToken;
}
