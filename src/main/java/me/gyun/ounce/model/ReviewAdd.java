package me.gyun.ounce.model;

import lombok.Data;

@Data
public class ReviewAdd {
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
}
