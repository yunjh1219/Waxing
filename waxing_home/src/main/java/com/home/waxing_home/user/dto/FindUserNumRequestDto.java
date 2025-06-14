package com.home.waxing_home.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;


@Getter
public class FindUserNumRequestDto {

    @NotBlank(message = "성함을 입력해주세요")
    private String name;

    @NotBlank(message = "이메일을 입력해주세요")
    private String email;

    @Builder
    public FindUserNumRequestDto(String name, String email){
        this.name = name;
        this.email = email;
    }
}
