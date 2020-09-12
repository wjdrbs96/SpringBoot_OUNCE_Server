package me.gyun.ounce.service;

import lombok.extern.slf4j.Slf4j;
import me.gyun.ounce.dto.FoodReviewAdd;
import me.gyun.ounce.dto.FoodReviewAll;
import me.gyun.ounce.mapper.ReviewMapper;
import me.gyun.ounce.model.DefaultRes;
import me.gyun.ounce.model.ReviewAdd;
import me.gyun.ounce.utils.ResponseMessage;
import me.gyun.ounce.utils.StatusCode;
import me.gyun.ounce.vo.ReviewAddIdx;
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
    public DefaultRes reviewRegister(ReviewAdd reviewAdd, String token) {
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
    public DefaultRes deleteReview(int reviewIdx, int profileIdx, String token) {
        try {
            JwtService.TOKEN decode = jwtService.decode(token);
            int[] profileIdxList = reviewMapper.findProfileIdx(decode.getUserIdx());

            // 유저당 최대 프로필 3개 가질 수 있음
            for (int idx : profileIdxList) {
                if (profileIdx == idx) {
                    // 삭제 가능
                    reviewMapper.deleteReview(reviewIdx);
                    return DefaultRes.res(StatusCode.OK, ResponseMessage.REVIEW_SUCCESS_DELETE);
                }
            }
            // 삭제 권한이 없음
            return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.REVIEW_NOT_DELETE);

        } catch (Exception e) {
            //Rollback
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(e.getMessage());
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }
}
