package me.gyun.ounce.controller;

import lombok.extern.slf4j.Slf4j;
import me.gyun.ounce.model.DefaultRes;
import me.gyun.ounce.model.ReviewAdd;
import me.gyun.ounce.service.ReviewService;
import me.gyun.ounce.utils.ResponseMessage;
import me.gyun.ounce.utils.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class ReviewController {

    private static final DefaultRes FAIL_DEFAULT_RES = new DefaultRes(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);

    private final ReviewService reviewService;

     /**
     * 생성자 의존성 주입
     * @param ReviewSerivce
     */
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
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
    @PostMapping("review/add")
    public ResponseEntity reviewAdd(@RequestHeader("token") String token, @RequestBody ReviewAdd reviewAdd) {
        try {
            return new ResponseEntity(reviewService.reviewRegister(reviewAdd, token), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 리뷰 삭제
     * @param token
     * @param reviewIdx
     */
    @DeleteMapping("review/delete/{reviewIdx}/{profileIdx}")
    public ResponseEntity reviewDelete(@PathVariable int reviewIdx, @PathVariable int profileIdx, @RequestHeader("token") String token) {
        try {
            reviewService.deleteReview(reviewIdx, profileIdx, token);
            return new ResponseEntity(reviewService.deleteReview(reviewIdx, profileIdx, token), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
