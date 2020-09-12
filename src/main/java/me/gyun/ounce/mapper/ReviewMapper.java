package me.gyun.ounce.mapper;

import me.gyun.ounce.dto.FoodReviewAdd;
import me.gyun.ounce.dto.FoodReviewAll;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReviewMapper {

    // 해당 프로필이 쓴 리뷰 총 개수
    int myReviewCount(@Param("profileIdx") int profileIdx);

    // 리뷰 전체 보기
    List<FoodReviewAll> foodReviewAll(@Param("foodIdx") int foodIdx);

    // 리뷰 작성
    int foodReviewAdd(FoodReviewAdd foodReviewAdd);

    // 유저가 가지고 있는 프로필 조회
    int[] findProfileIdx(int userIdx);

    // 리뷰 삭제
    void deleteReview(int reviewIdx);

}
