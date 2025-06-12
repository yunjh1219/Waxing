package com.home.waxing_home.global.filter;

import com.home.waxing_home.global.security.JwtProvider;
import com.home.waxing_home.user.service.ApiUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter; //요청 하나당 한 번만 실행되는 필터를 만들 때 상속해서 쓰는 추상 클래스

import java.io.IOException;

@RequiredArgsConstructor

//여기에 JWT 토큰을 읽고, 토큰이 유효하면 인증 정보를 SecurityContext에 저장하는 기능을 구현
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Request Header에서 JWT 토큰 추출
        String token = resolveToken(request);

        // 토큰 유효성 검사 후 인증 처리
        if (token != null && jwtProvider.isTokenValid(token)) {
            // 토큰이 유효할 경우 인증 객체 생성
            Authentication authentication = jwtProvider.getAuthentication(token);
            // SecurityContext에 인증 객체 설정
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 다음 필터로 이동
        filterChain.doFilter(request, response);
    }

    // Request Header에서 토큰 정보 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 부분 제거하고 토큰 반환
        }
        return null;
    }
}