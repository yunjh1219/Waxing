package com.home.waxing_home.user.service;

import com.home.waxing_home.global.error.exception.DuplicateUserNumException;
import com.home.waxing_home.user.dto.JoinRequestDto;
import com.home.waxing_home.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository; // 생성자 주입
    private final PasswordEncoder passwordEncoder;

    //회원가입(유저등록)
    @Transactional
    public void joinUsers(JoinRequestDto joinRequestDto) {

        //아이디 중복 확인
        if (userRepository.existsByUserNum(joinRequestDto.getUserNum())) {
            throw new DuplicateUserNumException(); //아이디 중복일 시 에러
        }

        joinRequestDto.passwordEncryption(passwordEncoder);

        /*
          joinRequestDto에 담긴 정보를
          toUserEntity()메서드를 호출해서 Entity 객체로 변환
          userRepository.save()를 통해 변환한 객체를 데이터베이스에 저장
        */
        userRepository.save(joinRequestDto.toUserEntity());
    }
}
