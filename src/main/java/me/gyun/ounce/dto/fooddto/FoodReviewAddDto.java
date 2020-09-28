package me.gyun.ounce.dto.fooddto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FoodReviewAddDto {
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
}
