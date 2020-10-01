package me.gyun.ounce.controller;

import lombok.extern.slf4j.Slf4j;
import me.gyun.ounce.dto.ResponseDto;
import me.gyun.ounce.utils.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Slf4j
public class AdviceController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity test(MethodArgumentNotValidException m) {
        List<String> errorList = new ArrayList<>();
        m.getBindingResult().getAllErrors().forEach(c -> {
            errorList.add(c.getDefaultMessage());
        });

        return new ResponseEntity(ResponseDto.res(StatusCode.BAD_REQUEST, errorList.get(0)), HttpStatus.BAD_REQUEST);
    }

}
