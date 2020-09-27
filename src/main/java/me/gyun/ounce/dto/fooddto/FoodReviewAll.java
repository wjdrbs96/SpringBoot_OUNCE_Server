package me.gyun.ounce.dto.fooddto;

import lombok.Data;

@Data
public class FoodReviewAll {
    private int profileIdx;
    private String profileURL;
    private String profileName;
    private int profileCatAge;
    private int reviewIdx;
    private String foodEvaluation;
    private int reviewRating;
    private int reviewPrefer;
    private int foodIdx;
}
