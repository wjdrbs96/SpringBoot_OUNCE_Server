package me.gyun.ounce.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.gyun.ounce.dto.ResponseDto;
import me.gyun.ounce.dto.user.SignInDto;
import me.gyun.ounce.dto.user.SignUpDto;
import me.gyun.ounce.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final ResponseDto responseDto = ResponseDto.FAIL_DEFAULT_RES;

    private final UserService userService;

    /**
     * 회원가입
     * @param @RequestBody SignUpDto
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<?>> signUp(@Valid @RequestBody SignUpDto signUpDto) {
        ResponseDto<?> responseDto = userService.userSignUp(signUpDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    /**
     * 로그인
     * @param @RequestBody SignInDto
     */
    @PostMapping("/signin")
    public ResponseEntity<ResponseDto<?>> signIn(@Valid @RequestBody SignInDto signInDto) {
        ResponseDto responseDto = userService.userSignIn(signInDto);

        if (responseDto.getStatusCode() == 500) {
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else if (responseDto.getStatusCode() == 400) {
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


}
