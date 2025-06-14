package com.home.waxing_home.user.service;

import com.home.waxing_home.global.error.exception.DuplicateUserNumException;
import com.home.waxing_home.global.error.exception.InvalidSigningInformation;
import com.home.waxing_home.global.error.exception.UserNotFoundException;
import com.home.waxing_home.global.security.JwtProvider;
import com.home.waxing_home.global.security.Token;
import com.home.waxing_home.user.domain.User;
import com.home.waxing_home.user.dto.FindUserNumRequestDto;
import com.home.waxing_home.user.dto.FindUserNumResponseDto;
import com.home.waxing_home.user.dto.JoinRequestDto;
import com.home.waxing_home.user.dto.LoginRequestDto;
import com.home.waxing_home.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    // 생성자 주입
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

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

    //로그인
    @Transactional
    public Token login(LoginRequestDto loginRequestDto) {
        String userNum = loginRequestDto.getUserNum();   // 아이디
        String password = loginRequestDto.getPassword(); // 비밀번호

        User user = userRepository.findByUserNum(userNum) // DB에 ID가 있는지 확인
                .orElseThrow(InvalidSigningInformation::new);

        if (!passwordEncoder.matches(password, user.getPassword())) { // 비밀번호가 맞는지 확인
            throw new InvalidSigningInformation();
        }


        Token token = jwtProvider.createToken(user.getUserNum(), user.getRole()); // JWT 액세스 토큰 + 리프레시 토큰 생성

        user.updateRefreshToken(token.getRefreshToken().getData()); // 리프레시 토큰을 User 엔티티에 저장

        return token;
    }

    //아이디 찾기
    @Transactional
    public FindUserNumResponseDto findUserNum(FindUserNumRequestDto findUserNumRequestDto){
        String name = findUserNumRequestDto.getName();
        String email = findUserNumRequestDto.getEmail();

        User user = userRepository.findByNameAndEmail(name, email)
                  .orElseThrow(UserNotFoundException::new);

        return FindUserNumResponseDto.builder()
                .userNum(user.getUserNum())
                .build();
    }

}
