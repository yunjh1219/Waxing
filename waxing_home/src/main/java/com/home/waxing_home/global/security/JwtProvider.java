package com.home.waxing_home.global.security;


import com.home.waxing_home.global.filter.CustomUserDetails;
import com.home.waxing_home.user.domain.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
@Getter
public class JwtProvider {

    private final Key secretKey;
    private final Long accessTokenExpirationPeriod;
    private final String accessHeader;
    private final Long refreshTokenExpirationPeriod;
    private final String refreshHeader;
    private static final String BEARER = "Bearer ";

    public JwtProvider(@Value("${jwt.secretKey}") String secretKey,
                       @Value("${jwt.access.expiration}") Long accessTokenExpirationPeriod,
                       @Value("${jwt.access.header}") String accessHeader,
                       @Value("${jwt.refresh.expiration}") Long refreshTokenExpirationPeriod,
                       @Value("${jwt.refresh.header}") String refreshHeader) {

        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.accessTokenExpirationPeriod = accessTokenExpirationPeriod;
        this.accessHeader = accessHeader;
        this.refreshTokenExpirationPeriod = refreshTokenExpirationPeriod;
        this.refreshHeader = refreshHeader;
    }

    public Token createToken(String userNum, Role role){
        AccessToken accessToken = AccessToken.builder()
                .header(accessHeader)
                .data(createAccessToken(userNum, role))
                .build();
        RefreshToken refreshToken = RefreshToken.builder()
                .header(refreshHeader)
                .data(createRefreshToken())
                .build();

        return Token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    //전달받은 JWT 토큰이 유효한지 검증하는 메서드입니다.
    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //JWT 토큰에서 사용자 정보를 꺼내서 Spring Security의 Authentication 객체로 바꾸는 메서드.
    // Spring Security는 Authentication 객체를 통해 로그인된 사용자인지 판단합니다.
    // 그래서 토큰 기반 인증을 하려면 토큰 → 사용자 정보 → Authentication 객체로 변환해야 합니다.
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = CustomUserDetails.of(extractToken(token));

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    //토큰 정보 추출
    public Claims extractToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)  //	JWT를 파싱하고 유효한지 검증
                    .getBody();             //	토큰의 Payload(내용물)를 Claims 객체로 꺼냄
        } catch (Exception e) {
            return null;
        }
    }

    private String createAccessToken(String userNum, Role role) {
        return Jwts.builder()
                .setClaims(Map.of("role", role)) //playload에 커스텀 데이터 넣음
                .setSubject(userNum) // 토큰의 주인 명시
                .setExpiration(expireTime(accessTokenExpirationPeriod)) //토큰 만료시간 설정
                .signWith(secretKey) // 위 설정을 secretkey로 서명해서 변조 되지 않게 보장
                .compact();
    }

    private String createRefreshToken() {
        return Jwts.builder()
                .setExpiration(expireTime(refreshTokenExpirationPeriod))
                .signWith(secretKey)
                .compact();
    }

    //"지금 시간 + 토큰 유효 시간"을 계산해서 언제 만료될지를 정해주는 함수
    private Date expireTime(Long expirationPeriod) {
        return new Date(System.currentTimeMillis() + expirationPeriod);
    }

}
