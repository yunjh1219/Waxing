package com.home.waxing_home.user.dto;

import com.home.waxing_home.user.domain.Role;
import com.home.waxing_home.user.domain.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

/* NotBlank
   해당 필드가 null이 아니고, 빈 문자열("")이나 공백(" ")이 아니어야 한다는 유효성 검사를 의미합니다.
   만약 빈 값이 들어오면, "아이디를 입력하세요" 라는 메시지와 함께 유효성 검사 오류가 발생합니다. */

@Getter
@Setter
public class JoinRequestDto {

    @NotBlank(message = "아이디를 입력하세요")
    private String userNum;
    @NotBlank(message = "비밀번호를 입력하세요")
    private String password;
    private String passwordConfirm;
    @NotBlank(message = "이름를 입력하세요")
    private String name;
    @NotBlank(message = "이메일을 입력하세요")
    private String email;
    @NotBlank(message = "폰번호를 입력하세요")
    private String phone;
    @NotBlank(message = "주소를 입력하세요")
    private String address;
    @NotBlank(message = "성별을 선택하세요")
    private String gender;
    private Role role = Role.USER; //기본 ROLE 유저로 설정

    //DTO를 실제 Entity 객체로 변환
    public User toUserEntity() {
        return User.builder()
                .userNum(this.userNum)      // DTO의 username을 User 엔티티의 userName에 매핑
                .password(this.password)      // 암호화된 비밀번호가 들어있다고 가정
                .name(this.name)
                .email(this.email)
                .phone(this.phone)
                .address(this.address)
                .gender(this.gender)
                .role(this.role)
                .build();
    }

    //비밀번호 암호화
    public void passwordEncryption(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

}
