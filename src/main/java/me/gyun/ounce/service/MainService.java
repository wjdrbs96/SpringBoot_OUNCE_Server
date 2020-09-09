package me.gyun.ounce.service;

import lombok.extern.slf4j.Slf4j;
import me.gyun.ounce.dto.MainHomeUp;
import me.gyun.ounce.dto.MainProfile;
import me.gyun.ounce.mapper.MainMapper;
import me.gyun.ounce.mapper.ReviewMapper;
import me.gyun.ounce.model.DefaultRes;
import me.gyun.ounce.utils.ResponseMessage;
import me.gyun.ounce.utils.StatusCode;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MainService {

    final JwtService jwtService;
    final MainMapper mainMapper;
    final ReviewMapper reviewMapper;

    /**
     * 생성자 의존성 주입
     *
     * @param JwtService
     * @param MainMapper
     */
    public MainService(JwtService jwtService, MainMapper mainMapper, ReviewMapper reviewMapper) {
        this.jwtService = jwtService;
        this.mainMapper = mainMapper;
        this.reviewMapper = reviewMapper;
    }

    public DefaultRes mainMyProfile(int profileIdx) {
        try {
            MainProfile mainProfile = mainMapper.MainProfileInquiry(profileIdx);
            int myReviewCount = reviewMapper.myReviewCount(profileIdx);
            MainHomeUp mainHomeUp = new MainHomeUp(mainProfile, myReviewCount);
            return DefaultRes.res(StatusCode.OK, ResponseMessage.PROFILE_MAIN_SUCCESS, mainHomeUp);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }
}
