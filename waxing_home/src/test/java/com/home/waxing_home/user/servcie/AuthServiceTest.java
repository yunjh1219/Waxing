package com.home.waxing_home.user.servcie;

import com.home.waxing_home.global.error.exception.DuplicateUserNumException;
import com.home.waxing_home.user.domain.User;
import com.home.waxing_home.user.dto.JoinRequestDto;
import com.home.waxing_home.user.repository.UserRepository;
import com.home.waxing_home.user.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;


import static org.codehaus.groovy.runtime.DefaultGroovyMethods.any;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @Test
    void 회원가입_성공() {
        // given
        JoinRequestDto dto = new JoinRequestDto();
        dto.setUserNum("testuser");
        dto.setPassword("1234");
        dto.setName("테스트유저");
        dto.setEmail("test@example.com");
        dto.setPhone("01012345678");
        dto.setAddress("서울시 강남구");

        when(userRepository.existsByUserNum("testuser")).thenReturn(false);
        when(passwordEncoder.encode("1234")).thenReturn("암호화된비밀번호");

        // 비밀번호 암호화 후 setter로 저장하는 구조일 경우
        // dto.passwordEncryption(passwordEncoder) 내부에서 setter 호출한다고 가정

        // when
        authService.joinUsers(dto);

    }

    @Test
    void 중복아이디_회원가입_예외() {
        // given
        JoinRequestDto dto = new JoinRequestDto();
        dto.setUserNum("duplicateUser");

        when(userRepository.existsByUserNum("duplicateUser")).thenReturn(true);

        // when & then
        assertThrows(DuplicateUserNumException.class, () -> {
            authService.joinUsers(dto);
        });
    }
}
