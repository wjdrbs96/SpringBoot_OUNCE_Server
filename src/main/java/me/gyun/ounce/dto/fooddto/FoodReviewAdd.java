package me.gyun.ounce.dto.fooddto;

import lombok.Data;

@Data
public class FoodReviewAdd {
    private int reviewIdx;
    private int reviewRating;
    private int reviewPrefer;
    private String foodEvaluation;
    private int stoolState;
    private int stoolSmell;
    private String trouble;
    private String reviewMemo;
    private String createdAt;
    private int foodIdx;
    private int profileIdx;
    private int userIdx;

    public FoodReviewAdd(int reviewRating, int reviewPrefer, String foodEvaluation, int stoolState, int stoolSmell, String trouble, String reviewMemo, String createdAt, int foodIdx, int profileIdx, int userIdx) {
        this.reviewRating = reviewRating;
        this.reviewPrefer = reviewPrefer;
        this.foodEvaluation = foodEvaluation;
        this.stoolState = stoolState;
        this.stoolSmell = stoolSmell;
        this.trouble = trouble;
        this.reviewMemo = reviewMemo;
        this.createdAt = createdAt;
        this.foodIdx = foodIdx;
        this.profileIdx = profileIdx;
        this.userIdx = userIdx;
    }
}
