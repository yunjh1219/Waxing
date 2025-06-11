package com.home.waxing_home.user.service;

import com.home.waxing_home.user.dto.JoinRequestDto;
import com.home.waxing_home.user.repository.AuthRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;

    //회원가입(유저등록)
    @Transactional
    public void joinUsers(JoinRequestDto requestdto){

    }

}
