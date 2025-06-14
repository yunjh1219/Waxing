package com.home.waxing_home.user.controller;

import com.home.waxing_home.global.common.SuccessResponse;
import com.home.waxing_home.global.security.AccessToken;
import com.home.waxing_home.global.security.RefreshToken;
import com.home.waxing_home.global.security.Token;
import com.home.waxing_home.user.dto.JoinRequestDto;
import com.home.waxing_home.user.dto.LoginRequestDto;
import com.home.waxing_home.user.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor //final 또는 @NonNull이 붙은 필드를 모두 파라미터로 받는 생성자를 자동으로 만들어 준다
public class AuthController {

    private final AuthService authService; //생성자 주입

    // 로그인
    @PostMapping("/api/login")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse<Void> login(@RequestBody @Valid LoginRequestDto loginDto, HttpServletResponse response) {
        Token token = authService.login(loginDto);

        setAccessToken(response, token.getAccessToken());
        setRefreshToken(response, token.getRefreshToken());

        return SuccessResponse.<Void>builder()
                .status(200)
                .message("로그인 성공")
                .build();
    }

    // 회원가입
    @PostMapping("/api/join")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse<Void> join(@RequestBody @Valid JoinRequestDto requestDto, HttpServletResponse response){

        authService.joinUsers(requestDto);

        return SuccessResponse.<Void>builder()
                .status(200)
                .message("회원 등록 성공")
                .build();
    }

    private void setAccessToken(HttpServletResponse response, AccessToken accessToken) {
        setHeader(response, accessToken.getHeader(), accessToken.getData());
    }

    private void setRefreshToken(HttpServletResponse response, RefreshToken refreshToken) {
        Cookie cookie = createCookie(refreshToken.getHeader(), refreshToken.getData());
        response.addCookie(cookie);
    }
    private Cookie createCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(RefreshToken.EXPIRATION_PERIOD);
        cookie.setHttpOnly(true);
        return cookie;
    }
    private void removeCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("Authorization-refresh", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);
    }
    private void setHeader(HttpServletResponse response, String header, String data) {
        response.setHeader(header, data);
    }

}
