package com.hyunprise.backend.domain.auth.controller;

import com.hyunprise.backend.domain.auth.services.AuthService;
import com.hyunprise.backend.domain.auth.vo.OAuth;
import com.hyunprise.backend.domain.auth.vo.OAuthResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("")
    public ResponseEntity<OAuthResult> getAuth(@RequestBody OAuth oAuth) {

        try {
            OAuthResult oAuthResult = authService.performOAuth(oAuth);
            return ResponseEntity.ok()
                    .header("Authorization", oAuthResult.getAccessToken())
                    .body(oAuthResult);
        }
        catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("reason", e.toString()).build();
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("reason", e.toString()).build();
        }
    }
}