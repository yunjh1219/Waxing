package com.home.waxing_home.global.error.exception;

public class DuplicateUserNumException extends RuntimeException {

    private static final String MESSAGE = "중복된 아이디입니다.";

    public DuplicateUserNumException() {
        super(MESSAGE);
    }
}
