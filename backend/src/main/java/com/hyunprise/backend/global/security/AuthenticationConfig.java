package com.hyunprise.backend.global.security;

//import com.hyunprise.backend.global.utils.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//https://jaehoney.tistory.com/249
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthenticationConfig {

//    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .cors()
                .and()
                .authorizeRequests()
//                .antMatchers("/auth").anonymous()
//                .antMatchers("/member/**").anonymous()
//                .antMatchers("/issued_coupon/**").authenticated()
//                .anyRequest().authenticated()
                .anyRequest().anonymous()
                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
