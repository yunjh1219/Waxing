package com.home.waxing_home.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FindUserNumResponseDto {
    private String userNum;

    @Builder
    public FindUserNumResponseDto(String userNum){
        this.userNum = userNum;
    }

}
