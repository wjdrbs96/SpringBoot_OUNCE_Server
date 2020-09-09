package me.gyun.ounce.controller;

import lombok.extern.slf4j.Slf4j;
import me.gyun.ounce.model.DefaultRes;
import me.gyun.ounce.service.MainService;
import me.gyun.ounce.utils.ResponseMessage;
import me.gyun.ounce.utils.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("main")
public class MainController {

    private static final DefaultRes FAIL_DEFAULT_RES = new DefaultRes(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);

    final MainService mainService;

    /**
     * 생성자 의존성 주입
     *
     * @param mainService
     *
     */
    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    /**
     * 메인 화면 상단 나의 프로필 조회
     *
     * @param token
     * @param profileIdx
     */
    @GetMapping("/profile/{profileIdx}")
    public ResponseEntity mainProfile(@PathVariable int profileIdx) {
        try {
            return new ResponseEntity(mainService.mainMyProfile(profileIdx), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
