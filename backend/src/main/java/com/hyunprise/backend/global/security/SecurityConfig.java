package com.hyunprise.backend.global.security;

import com.hyunprise.backend.global.security.filters.JwtFilterFactory;
import com.hyunprise.backend.global.security.types.MemberRoles;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

//https://jaehoney.tistory.com/249
//https://github.com/codingspecialist/Springboot-Security-JWT-Easy/blob/master/src/main/java/com/cos/jwtex01/config/jwt/JwtAuthorizationFilter.java
//https://100100e.tistory.com/387

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtFilterFactory filterFactory;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .cors()
                .and()
                .authorizeRequests()
                    .antMatchers("/auth").permitAll()
                .and()
                .authorizeRequests()
                    .antMatchers("/**").hasRole(MemberRoles.DEV.role())
                    .antMatchers("/member/**").hasRole(MemberRoles.MEMBER.role())
                    .antMatchers("/coupon/**").hasRole(MemberRoles.SELLER.role())
                    .antMatchers("/issued_coupon/**").hasRole(MemberRoles.MEMBER.role())
                .and()
                .addFilterBefore(filterFactory.testAuthorization(), BasicAuthenticationFilter.class)
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .build();
    }
}
