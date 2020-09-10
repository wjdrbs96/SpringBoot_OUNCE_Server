package me.gyun.ounce.vo;

import lombok.Data;

@Data
public class ReviewAddIdx {
    private int reviewIdx;

    public ReviewAddIdx(int reviewIdx) {
        this.reviewIdx = reviewIdx;
    }
}
