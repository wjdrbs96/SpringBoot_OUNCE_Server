package me.gyun.ounce.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.gyun.ounce.dto.ResponseDto;
import me.gyun.ounce.dto.profile.ProfileRegisterDto;
import me.gyun.ounce.service.ProfileService;
import me.gyun.ounce.utils.StatusCode;
import me.gyun.ounce.utils.auth.Auth;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @Auth
    @PostMapping("/register")
    public ResponseEntity<ResponseDto<?>> profileRegister(@RequestHeader("accessToken") String accessToken,
                                                          @RequestPart(value = "profileImg") MultipartFile multipartFile,
                                                          @Valid ProfileRegisterDto profileRegisterDto) {
        if (multipartFile == null) {
            return new ResponseEntity<>(ResponseDto.res(StatusCode.BAD_REQUEST, "필요한 값이 없습니다"), HttpStatus.BAD_REQUEST);
        }

        profileRegisterDto.setProfileImg(multipartFile);
        ResponseDto<?> responseDto = profileService.profileRegister(profileRegisterDto, accessToken);
        if (responseDto.getStatusCode() == 500) {
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
