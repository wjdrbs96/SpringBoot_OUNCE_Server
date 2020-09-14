package me.gyun.ounce.dto;

import lombok.Data;
import me.gyun.ounce.dto.MainProfile;

@Data
public class MainHomeUp {

    private MainProfile myProfileInfo;
    private int myReviewCount;

    public MainHomeUp(MainProfile myProfileInfo, int myReviewCount) {
        this.myProfileInfo = myProfileInfo;
        this.myReviewCount = myReviewCount;
    }
}
