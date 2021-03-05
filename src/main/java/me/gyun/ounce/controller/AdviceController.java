package me.gyun.ounce.controller;

import lombok.extern.slf4j.Slf4j;
import me.gyun.ounce.dto.ResponseDto;
import me.gyun.ounce.exception.AlreadyUser;
import me.gyun.ounce.utils.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Slf4j
public class AdviceController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity notValue(MethodArgumentNotValidException m) {
        List<String> errorList = new ArrayList<>();
        m.getBindingResult().getAllErrors().forEach(c -> {
            errorList.add(c.getDefaultMessage());
        });

        return new ResponseEntity(ResponseDto.res(StatusCode.BAD_REQUEST, errorList.get(0)), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity notHeaders(MissingRequestHeaderException m) {
        return new ResponseEntity(ResponseDto.res(StatusCode.BAD_REQUEST, m.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity bindException(BindException b) {
        return new ResponseEntity(ResponseDto.res(StatusCode.BAD_REQUEST, b.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity notFoundException(BindException b) {
        return new ResponseEntity(ResponseDto.res(StatusCode.BAD_REQUEST, b.getMessage()), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = AlreadyUser.class)
    public ResponseEntity<Object> test(AlreadyUser ex) {
        ex.printStackTrace();
        return ResponseEntity.ok() // 502
                .body(ResponseDto.res(200, "이미 유저가 존재합니다 " + ex.getMessage()));
    }

}
