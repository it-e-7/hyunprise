//package com.hyunprise.backend.domain.auth.utils;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@RequiredArgsConstructor
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    private final AuthTokenProvider authTokenProvider;
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**");
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        JwtAuthenticationFilter jwtAuthFilter = new JwtAuthenticationFilter(authTokenProvider);
//
//        http
//                .authorizeRequests()
//                .antMatchers(HttpMethod.OPTIONS).permitAll()
//                .antMatchers("/auth/**").permitAll()
//                .anyRequest().authenticated().and() // 해당 요청을 인증된 사용자만 사용 가능
//                .headers()
//                .frameOptions()
//                .sameOrigin().and()
//                .cors().and()
//                .csrf().disable()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//
//    }
//}