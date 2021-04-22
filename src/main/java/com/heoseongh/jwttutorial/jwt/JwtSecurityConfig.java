package com.heoseongh.jwttutorial.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final TokenProvider tokenProvider;

    // 커스텀한 jwt 필터를 추가해준다.
    @Override
    public void configure(HttpSecurity http) throws Exception {
        JwtFilter customJwtFilter = new JwtFilter(tokenProvider);

        // addFilterBefore : UsernamePasswordAuthenticationFilter 보다 먼저 실행된다.
        // addFilterAfter : UsernamePasswordAuthenticationFilter 다음에 실행된다.
        http.addFilterBefore(customJwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
