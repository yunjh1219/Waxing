package com.home.waxing_home.global.security;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AccessToken {
    private String header;
    private String data;
    public static final int EXPIRATION_PERIOD = 7200;

    @Builder
    public AccessToken(String header, String data) {
        this.header = header;
        this.data = data;
    }
}