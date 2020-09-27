package me.gyun.ounce.controller;

import lombok.extern.slf4j.Slf4j;
import me.gyun.ounce.model.DefaultRes;
import me.gyun.ounce.model.LoginIdCheck;
import me.gyun.ounce.model.SignInModel;
import me.gyun.ounce.model.SignUpModel;
import me.gyun.ounce.service.AuthService;
import me.gyun.ounce.utils.ResponseMessage;
import me.gyun.ounce.utils.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("user")
public class LoginController {

    // 실패시 사용
    private static final DefaultRes FAIL_DEFAULT_RES = new DefaultRes(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);

    private final AuthService authService;

    /**
     * AuthService 생성자 의존성 주입
     *
     * @param AuthService
     */
    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * 회원가입
     * @Valid 유효성 검증
     *
     * @return ResponseEntity
     */
    @PostMapping("/signUp")
    public ResponseEntity signUp(@Valid @RequestBody SignUpModel signUpModel) {
        try {
            return new ResponseEntity(authService.signUp(signUpModel), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 로그인
     *
     * @Valid 유효성 검증
     * @return ResponseEntity
     */
    @PostMapping("/signIn")
    public ResponseEntity signIn(@Valid @RequestBody SignInModel signInModel) {
        try {
            return new ResponseEntity(authService.signIn(signInModel), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 아이디 중복체크
     *
     * @Valid 유효성 검증
     * @return ResponseEntity
     */
    @PostMapping("/checkId")
    public ResponseEntity checkId(@Valid @RequestBody LoginIdCheck loginIdCheck) {
        try {
            return new ResponseEntity(authService.checkLoginId(loginIdCheck.getId()), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
