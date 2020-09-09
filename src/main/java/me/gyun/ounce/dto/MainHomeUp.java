package me.gyun.ounce.dto;

import lombok.Data;

@Data
public class MainHomeUp {

    private MainProfile myProfileInfo;
    private int myReviewCount;

    public MainHomeUp(MainProfile myProfileInfo, int myReviewCount) {
        this.myProfileInfo = myProfileInfo;
        this.myReviewCount = myReviewCount;
    }
}
