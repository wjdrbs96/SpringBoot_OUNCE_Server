package me.gyun.ounce.service;

import lombok.extern.slf4j.Slf4j;
import me.gyun.ounce.dto.fooddto.FoodReviewAdd;
import me.gyun.ounce.dto.fooddto.FoodReviewAll;
import me.gyun.ounce.mapper.ReviewMapper;
import me.gyun.ounce.model.DefaultRes;
import me.gyun.ounce.model.ReviewModel;
import me.gyun.ounce.utils.ResponseMessage;
import me.gyun.ounce.utils.StatusCode;
import me.gyun.ounce.dto.reviewdto.ReviewAddIdx;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Service
@Slf4j
public class ReviewService {

    private final ReviewMapper reviewMapper;
    private final JwtService jwtService;

    /**
     * 생성자 의존성 주입
     *
     * @param ReviewMapper
     */
    public ReviewService(ReviewMapper reviewMapper, JwtService jwtService) {
        this.reviewMapper = reviewMapper;
        this.jwtService = jwtService;
    }

    /**
     * 캣 푸드 리뷰 전체 보기
     *
     * @param foodIdx
     */
    public DefaultRes foodReviewAll(int foodIdx) {
        try {
            List<FoodReviewAll> foodReviewAll = reviewMapper.foodReviewAll(foodIdx);
            return DefaultRes.res(StatusCode.OK, ResponseMessage.FOOD_REVIEW_RESULT, foodReviewAll);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }

    /**
     * 캣 푸드 리뷰 전체 보기
     *
     * @param foodIdx
     */
    @Transactional
    public DefaultRes reviewRegister(ReviewModel reviewAdd, String token) {
        try {
            JwtService.TOKEN decode = jwtService.decode(token);
            FoodReviewAdd foodReviewAdd = new FoodReviewAdd(reviewAdd.getReviewRating(), reviewAdd.getReviewPrefer(), reviewAdd.getFoodEvaluation(), reviewAdd.getStoolState(), reviewAdd.getStoolSmell(), reviewAdd.getTrouble(), reviewAdd.getReviewMemo(), reviewAdd.getCreatedAt(), reviewAdd.getFoodIdx(), reviewAdd.getProfileIdx(), decode.getUserIdx());

            reviewMapper.foodReviewAdd(foodReviewAdd);
            ReviewAddIdx reviewAddIdx = new ReviewAddIdx(foodReviewAdd.getReviewIdx());
            return DefaultRes.res(StatusCode.OK, ResponseMessage.REVIEW_REGISTER_SUCCESS, reviewAddIdx);
        } catch (Exception e) {
            //Rollback
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(e.getMessage());
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }

    }

    /**
     * 캣 푸드 리뷰 삭제
     *
     * @param profileIdx
     * @param reviewIdx
     * @param token
     */
    @Transactional
    public DefaultRes deleteReview(int reviewIdx) {
        try {
            reviewMapper.deleteReview(reviewIdx);
            return DefaultRes.res(StatusCode.OK, ResponseMessage.REVIEW_SUCCESS_DELETE);
        } catch (Exception e) {
            //Rollback
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(e.getMessage());
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }

}
