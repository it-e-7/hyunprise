package com.hyunprise.backend.global.security.utils;

import com.hyunprise.backend.domain.auth.vo.OAuth;
import com.hyunprise.backend.domain.member.vo.Member;
import com.hyunprise.backend.global.security.types.MemberRole;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtUtil {

    private final Key key;

    @Value("${jwt.expiration-seconds}")
    private Long expireSecond;

    public JwtUtil(@Value("${jwt.secret}") String secretKey) {
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public String jwtTokenOfMember(Member member) {
        return Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaims(member))
                .signWith(key)
                .setExpiration(createExpiredDate()).compact();
    }

    public boolean isValidToken(String token) {
        if (token == null) return false;
        try {
            getClaimsFromToken(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            System.out.println("Invalid JWT Token");
        } catch (ExpiredJwtException e) {
            System.out.println("Expired JWT Token");
        } catch (UnsupportedJwtException e) {
            System.out.println("unsupported JWT Token");
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty.");
        }
        return false;
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            return null;
        }
        return parseTokenFromAuthorizationHeader(header);
    }

    public String parseTokenFromAuthorizationHeader(String header) {
        return header.split(" ")[1];
    }

    public String getTokenFromAuthentication(Authentication authentication) {
        try {
            return authentication.getCredentials().toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public Date createExpiredDate() {
        return Date.from(Instant.now().plusSeconds(expireSecond));
    }

    private Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();

        header.put("typ", "JWT");
        header.put("alg", "HS256");
        header.put("regDate", System.currentTimeMillis());
        return header;
    }

    private Map<String, Object> createClaims(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("memberUUID", member.getMemberUUID());
        claims.put("memberEmail", member.getMemberEmail());
        claims.put("memberName", member.getMemberName());
        claims.put("accountType", member.getAccountType());
        return claims;
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key)
                .build().parseClaimsJws(token).getBody();
    }

    public String getMemberUUIDFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("memberUUID").toString();
    }

    public List<GrantedAuthority> authorityOf(MemberRole role) {
        if (role == null) return Collections.emptyList();
        return Stream.of(new SimpleGrantedAuthority("ROLE_" + role.name())).collect(Collectors.toList());
    }

    public Authentication authenticationFromOAuth(OAuth oAuth) {
        return new UsernamePasswordAuthenticationToken(oAuth.getMemberUUID(), oAuth.getAuthToken());
    }

    public Authentication jwtAuthenticationFromValidToken(String token) {
        Claims claims = getClaimsFromToken(token);
        String memberUUID = claims.get("memberUUID", String.class);
        Integer accountType = claims.get("accountType", Integer.class);
        MemberRole role = MemberRole.roleOfType(accountType);

        return new UsernamePasswordAuthenticationToken(memberUUID, token, authorityOf(role));
    }

}