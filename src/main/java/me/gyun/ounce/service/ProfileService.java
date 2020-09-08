package me.gyun.ounce.service;

import lombok.extern.slf4j.Slf4j;
import me.gyun.ounce.dto.Profile;
import me.gyun.ounce.mapper.ProfileMapper;
import me.gyun.ounce.model.DefaultRes;
import me.gyun.ounce.model.ProfileModel;
import me.gyun.ounce.utils.ResponseMessage;
import me.gyun.ounce.utils.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Slf4j
@Service
public class ProfileService {

    private final S3FileUploadService s3FileUploadService;
    private final ProfileMapper profileMapper;
    private final JwtService jwtService;

    /**
     * 생성자 의존성 주입
     *
     * @param S3FileUploadService
     * @Param ProfileMapper
     */
    public ProfileService(S3FileUploadService s3FileUploadService, ProfileMapper profileMapper, JwtService jwtService) {
        this.s3FileUploadService = s3FileUploadService;
        this.profileMapper = profileMapper;
        this.jwtService = jwtService;
    }

    /**
     * 고양이 프로필 등록
     *
     * @Param ProfileModel
     */
    @Transactional
    public DefaultRes register(ProfileModel profileModel, String token) {
        try {
            if (profileModel != null) {
                profileModel.setProfileURL(s3FileUploadService.upload(profileModel.getProfileImg()));
            }
            Profile profile = new Profile(profileModel.getProfileName(), profileModel.getProfileURL(),
                                          profileModel.getProfileCatWeight(), profileModel.isProfileCatNeutral(),
                                          profileModel.getProfileCatAge(), profileModel.getProfileInfo());

            JwtService.TOKEN decode = jwtService.decode(token);
            profileMapper.profileRegister(profile, decode.getUserIdx());
            System.out.println(profile.getProfileIdx());
            return DefaultRes.res(StatusCode.OK, ResponseMessage.PROFILE_REGISTER_SUCCESS, profile.getProfileIdx());
        } catch (Exception e) {
            //Rollback
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(e.getMessage());
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }

    /**
     * 고양이 프로필 수정
     *
     * @param ProfileModel
     * @param token
     */
    @Transactional
    public DefaultRes update(ProfileModel profileModel, String token, int profileIdx) {
        try {
            if (profileModel.getProfileImg() != null) {
                profileModel.setProfileURL(s3FileUploadService.upload(profileModel.getProfileImg()));
            }
            Profile profile = new Profile(profileModel.getProfileName(), profileModel.getProfileURL(),
                    profileModel.getProfileCatWeight(), profileModel.isProfileCatNeutral(),
                    profileModel.getProfileCatAge(), profileModel.getProfileInfo());

            JwtService.TOKEN decode = jwtService.decode(token);

            int check = profileMapper.isMyProfile(profileIdx, decode.getUserIdx());

            // 프로필 수정 가능
            if (check > 0) {
                profileMapper.profileUpdate(profile, decode.getUserIdx());
                return DefaultRes.res(StatusCode.OK, ResponseMessage.PROFILE_UPDATE_SUCCESS);
            }

            // 프로필 수정 불가능
            return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.PROFILE_NOT_AUTHORITY);
        } catch (Exception e) {
            //Rollback
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(e.getMessage());
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }

    /**
     * 고양이 프로필 등록 개수 제한
     *
     * @Param token
     */
    public DefaultRes profileRegisterLimit(final String token) {
        try {
            JwtService.TOKEN decode = jwtService.decode(token);
            int profileCount = profileMapper.profileRegisterLimit(decode.getUserIdx());
            if (profileCount <= 3) {
                return DefaultRes.res(StatusCode.OK, ResponseMessage.PROFILE_REGISTER_POSSIBLE, true);
            }
            return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.PROFILE_REGISTER_IMPOSSIBLE, false);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }

}
