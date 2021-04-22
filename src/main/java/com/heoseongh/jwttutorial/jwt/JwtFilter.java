package com.heoseongh.jwttutorial.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    private final TokenProvider tokenProvider;

    /**
     * jwt 토큰의 인증 정보를 SecurityContext에 저장한다.
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // 1. ServletRequest 를 받아서 HttpServletRequest 으로 다운캐스팅 해준다.
        // 2. 만들어둔 resolveToken 메서드를 통해서 jwt 토큰을 얻는다.
        // 3. 요청에서 URI를 가져온다.

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String jwt = resolveToken(httpServletRequest);
        String requestURI = httpServletRequest.getRequestURI();

        if(StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            Authentication authentication = tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }
    }


    /**
     * Request Header 에서 JWT 토큰을 꺼내서 반환해준다.
     * @param request
     * @return JWT Token
     */
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        // 토큰이 공백, null, empty 가 아닌 문자열이고 Bearer로 시작하는지 확인하고
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            // 앞에 "Bearer" 이후의 순수한 토큰만 잘라서 리턴한다.
            return bearerToken.substring(7);
        }
        return null;
    }
}
