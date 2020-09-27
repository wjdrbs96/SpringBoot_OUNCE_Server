package me.gyun.ounce.controller;

import lombok.extern.slf4j.Slf4j;
import me.gyun.ounce.model.DefaultRes;
import me.gyun.ounce.model.ProfileModel;
import me.gyun.ounce.service.JwtService;
import me.gyun.ounce.service.ProfileService;
import me.gyun.ounce.utils.ResponseMessage;
import me.gyun.ounce.utils.StatusCode;
import me.gyun.ounce.utils.auth.Auth;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("profile")
public class ProfileController {

    private static final DefaultRes FAIL_DEFAULT_RES = new DefaultRes(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);
    private static final DefaultRes UNAUTHORIZED_RES = new DefaultRes(StatusCode.UNAUTHORIZED, ResponseMessage.UNAUTHORIZED);

    private final ProfileService profileService;
    private final JwtService jwtService;

    /**
     * ProfileService 생성자 의존성 주입
     *
     * @param ProfileService
     */
    public ProfileController(ProfileService profileService, JwtService jwtService) {
        this.profileService = profileService;
        this.jwtService = jwtService;
    }

    /**
     * 프로필 등록
     *
     * @param ProfileModel
     * @param Token
     * @param ProfileImg
     */
    @Auth
    @PostMapping("/register/{userIdx}")
    public ResponseEntity profileRegister(@Valid ProfileModel profileModel,
                                          @RequestHeader("token") String token,
                                          @RequestPart(value = "profile", required = false) final MultipartFile profileImg,
                                          @PathVariable int userIdx) {
        try {
            if (jwtService.checkAuth(token, userIdx)) {
                if (profileImg != null) {
                    profileModel.setProfileImg(profileImg);
                }
                return new ResponseEntity(profileService.register(profileModel, userIdx), HttpStatus.OK);
            }
            return new ResponseEntity(UNAUTHORIZED_RES, HttpStatus.UNAUTHORIZED);
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
    @Auth
    @PutMapping("update/{profileIdx}/{userIdx}")
    public ResponseEntity profileUpdate(@PathVariable int profileIdx,
                                        @PathVariable int userIdx,
                                        @RequestHeader("token") String token,
                                        ProfileModel profileModel,
                                        @RequestPart(value = "profile", required = false) final MultipartFile profileImg) {
        try {
            if (jwtService.checkAuth(token, userIdx)) {
                if (profileImg != null) {
                    profileModel.setProfileImg(profileImg);
                }
                return new ResponseEntity(profileService.update(profileModel, userIdx, profileIdx), HttpStatus.OK);
            }
            // 수정 권한 없음
            return new ResponseEntity(UNAUTHORIZED_RES, HttpStatus.UNAUTHORIZED);
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
    @Auth
    @GetMapping("/limit")
    public ResponseEntity profileAddLimit(@RequestHeader("token") String token) {
        try {
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
    @Auth
    @GetMapping("/conversion/{profileIdx}/{userIdx}")
    public ResponseEntity profileConversion(@RequestHeader("token") String token, @PathVariable int profileIdx, @PathVariable int userIdx) {
        try {
            if (jwtService.checkAuth(token, userIdx)) {
                return new ResponseEntity(profileService.conversion(profileIdx, userIdx), HttpStatus.OK);
            }
            return new ResponseEntity(UNAUTHORIZED_RES, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
