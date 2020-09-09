package me.gyun.ounce.controller;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import me.gyun.ounce.model.DefaultRes;
import me.gyun.ounce.model.ProfileModel;
import me.gyun.ounce.service.ProfileService;
import me.gyun.ounce.service.S3FileUploadService;
import me.gyun.ounce.utils.ResponseMessage;
import me.gyun.ounce.utils.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("profile")
public class ProfileController {

    private static final DefaultRes FAIL_DEFAULT_RES = new DefaultRes(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);

    private final ProfileService profileService;

    /**
     * ProfileService 생성자 의존성 주입
     *
     * @param ProfileService
     */
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    /**
     * 프로필 등록
     *
     * @param ProfileModel
     * @param Token
     * @param ProfileImg
     */
    @PostMapping("/register")
    public ResponseEntity profileRegister(ProfileModel profileModel,
                                          @RequestHeader("token") String token,
                                          @RequestPart(value = "profile", required = false) final MultipartFile profile) {
        try {
            if (profile != null) {
                profileModel.setProfileImg(profile);
            }
            if (token == null) {
                return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.NULL_VALUE), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity(profileService.register(profileModel, token), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 프로필 수정
     *
     * @param ProfileModel
     * @param Token
     * @param ProfileImg
     * @param profileIdx
     */
    @PutMapping("update/{profileIdx}")
    public ResponseEntity profileUpdate(@PathVariable int profileIdx,
                                        @RequestHeader("token") String token,
                                        ProfileModel profileModel,
                                        @RequestPart(value = "profile", required = false) final MultipartFile profile) {
        try {
            if (profile != null) {
                profileModel.setProfileImg(profile);
            }
            if (token == null) {
                return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.NULL_VALUE), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity(profileService.update(profileModel, token, profileIdx), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 프로필 개수 제한 (3개)
     *
     * @param Token
     */
    @GetMapping("/addLimit")
    public ResponseEntity profileAddLimit(@RequestHeader("token") String token) {
        try {
            if (token == null) {
                return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.NULL_VALUE), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity(profileService.profileRegisterLimit(token), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 프로필 전환
     *
     * @param Token
     * @param profileIdx
     */
    @GetMapping("/conversion/{profileIdx}")
    public ResponseEntity profileConversion(@RequestHeader("token") String token, @PathVariable int profileIdx) {
        try {
            return new ResponseEntity(profileService.conversion(profileIdx, token), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
