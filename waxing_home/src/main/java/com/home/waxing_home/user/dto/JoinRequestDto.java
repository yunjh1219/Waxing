package com.home.waxing_home.user.dto;

import com.home.waxing_home.user.domain.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/* NotBlank
   해당 필드가 null이 아니고, 빈 문자열("")이나 공백(" ")이 아니어야 한다는 유효성 검사를 의미합니다.
   만약 빈 값이 들어오면, "아이디를 입력하세요" 라는 메시지와 함께 유효성 검사 오류가 발생합니다. */

@Getter
@Setter
public class JoinRequestDto {

    @NotBlank(message = "아이디를 입력하세요")
    private String username;
    private String password;
    private String passwordConfirm;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String gender;
    private Role role = Role.USER; //기본 ROLE 유저로 설정

    // 약관 동의 필드 추가 (기본 false)
    private boolean agreeTerms;
    private boolean agreePrivacyPurpose;
    private boolean agreePrivacyPeriod;

}
