package me.gyun.ounce.controller;

import lombok.extern.slf4j.Slf4j;
import me.gyun.ounce.model.DefaultRes;
import me.gyun.ounce.model.ReviewModel;
import me.gyun.ounce.service.JwtService;
import me.gyun.ounce.service.ReviewService;
import me.gyun.ounce.utils.ResponseMessage;
import me.gyun.ounce.utils.StatusCode;
import me.gyun.ounce.utils.auth.Auth;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class ReviewController {

    private static final DefaultRes FAIL_DEFAULT_RES = new DefaultRes(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);
    private static final DefaultRes UNAUTHORIZED_RES = new DefaultRes(StatusCode.UNAUTHORIZED, ResponseMessage.UNAUTHORIZED);

    private final ReviewService reviewService;
    private final JwtService jwtService;

     /**
     * 생성자 의존성 주입
     * @param ReviewSerivce
     */
    public ReviewController(ReviewService reviewService, JwtService jwtService) {
        this.reviewService = reviewService;
        this.jwtService = jwtService;
    }

     /**
     * 음식 리뷰 전체 보기
     * @param foodIdx
     */
    @GetMapping("/food/reviewAll/{foodIdx}")
    public ResponseEntity foodReviewAll(@PathVariable int foodIdx) {
        try {
            return new ResponseEntity(reviewService.foodReviewAll(foodIdx), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 리뷰 작성
     * @param token
     * @param ReviewAdd
     */
    @Auth
    @PostMapping("review/add/:userIdx")
    public ResponseEntity reviewAdd(@RequestHeader("token") String token, @RequestBody ReviewModel reviewAdd, @PathVariable int userIdx) {
        try {
            if (jwtService.checkAuth(token, userIdx)) {
                return new ResponseEntity(reviewService.reviewRegister(reviewAdd, token), HttpStatus.OK);
            }
            return new ResponseEntity(UNAUTHORIZED_RES, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 리뷰 삭제
     * @param token
     * @param reviewIdx
     * @param profileIdx
     */
    @DeleteMapping("review/delete/{reviewIdx}/{userIdx}")
    public ResponseEntity reviewDelete(@PathVariable int reviewIdx, @PathVariable int userIdx, @RequestHeader("token") String token) {
        try {
            if (jwtService.checkAuth(token, userIdx)) {
                return new ResponseEntity(reviewService.deleteReview(reviewIdx), HttpStatus.OK);
            }
            return new ResponseEntity(UNAUTHORIZED_RES, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
