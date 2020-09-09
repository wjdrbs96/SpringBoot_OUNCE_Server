package me.gyun.ounce.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReviewMapper {

    // 해당 프로필이 쓴 리뷰 총 개수
    int myReviewCount(int profileIdx);
}
