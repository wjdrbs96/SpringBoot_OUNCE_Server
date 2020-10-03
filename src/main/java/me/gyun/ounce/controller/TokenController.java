package me.gyun.ounce.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.gyun.ounce.dto.ResponseDto;
import me.gyun.ounce.service.JwtService;
import me.gyun.ounce.utils.ResponseMessage;
import me.gyun.ounce.utils.StatusCode;
import me.gyun.ounce.utils.auth.RefreshAuth;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("token")
public class TokenController {

    private final JwtService jwtService;

    @RefreshAuth
    @GetMapping("/re-request")
    public ResponseEntity<?> tokenRequest(@RequestHeader("refreshToken") String refreshToken) {
        JwtService.TOKEN refreshDecode = jwtService.refreshDecode(refreshToken);
        ResponseDto responseDto = new ResponseDto(StatusCode.OK, ResponseMessage.TOKEN_REQUEST, jwtService.create(refreshDecode.getUserIdx()));
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
