package me.gyun.ounce.dto.maindto;

import lombok.Data;

@Data
public class MainHomeUpDto {

    private MainProfileDto myProfileInfo;
    private int myReviewCount;

    public MainHomeUpDto(MainProfileDto myProfileInfo, int myReviewCount) {
        this.myProfileInfo = myProfileInfo;
        this.myReviewCount = myReviewCount;
    }
}
