package com.home.waxing_home.user.controller;

import com.home.waxing_home.global.common.SuccessResponse;
import com.home.waxing_home.user.dto.JoinRequestDto;
import com.home.waxing_home.user.service.AuthService;
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


    // 회원가입
    @PostMapping("/api/join/users")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse<Void> signUpUsers(@RequestBody @Valid JoinRequestDto requestDto, HttpServletResponse response){

        authService.joinUsers(requestDto);

        return SuccessResponse.<Void>builder()
                .status(200)
                .message("회원 등록 성공")
                .build();
    }


}
