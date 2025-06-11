package com.home.waxing_home.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter // 값을 읽는 것만 허용. 그렇기에 Setter은 불필요.
@RequiredArgsConstructor // final이나 @NonNull이 붙은 필드만을 매개변수로 받는 생성자를 자동 생성
public enum Role {
    ADMIN("ROLE_ADMIN"), USER("ROLE_USER");

    private final String key;

    public static Role ofValue(String value) {
        if ("ROLE_ADMIN".equals(value)) {
            return Role.ADMIN;
        } else if ("ROLE_USER".equals(value)) {
            return Role.USER;
        } else {
            throw new IllegalArgumentException("해당 권한은 없는 권한입니다: " + value);
        }
    }
}