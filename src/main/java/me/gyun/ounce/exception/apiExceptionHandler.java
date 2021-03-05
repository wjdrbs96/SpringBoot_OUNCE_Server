package me.gyun.ounce.exception;

import me.gyun.ounce.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class apiExceptionHandler {

    @ExceptionHandler(AlreadyUser.class)
    public ResponseEntity<?> alreadyUser() {
        return new ResponseEntity<>(ResponseDto.res(400, "유저가 이미 존재합니다"), HttpStatus.BAD_REQUEST);
    }
}
