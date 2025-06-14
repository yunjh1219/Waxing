package com.home.waxing_home.user.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity //이 클래스가 JPA의 엔티티 클래스임을 나타냅니다.
@Getter
@Setter
@NoArgsConstructor

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키의 값을 자동 생성, IDNTITY는 자동으로 증가를 위함
    @Column(name = "user_id") // DB컬럼명과 자바 변수명이 다를 때 선언
    private Long id;      // 이 필드를 DB 테이블의 user_id 컬럼에 매핑

    @Column(nullable = false, unique = true, length = 30) // 널값·중복 불가 , 글자수 30 제한
    private String userNum; // 아이디

    @Column(nullable = false)
    private String password; // 비밀번호

    @Column(nullable = false)
    private String name;     // 이름

    @Column(nullable = false)
    private String email;    // 이메일

    @Column(nullable = false)
    private String phone;    // 폰번호

    @Column(nullable = false)
    private String address;  // 주소


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    private String refreshToken;

    @Builder
    public User(String userNum, String password, String name, String email, String phone,
                String address, Role role, String refreshToken) {
        this.userNum = userNum;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;

        this.role = role;
        this.refreshToken = refreshToken;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
