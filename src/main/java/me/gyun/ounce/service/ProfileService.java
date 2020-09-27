package me.gyun.ounce.service;

import lombok.extern.slf4j.Slf4j;
import me.gyun.ounce.dto.profiledto.Profile;
import me.gyun.ounce.dto.profiledto.ProfileConversion;
import me.gyun.ounce.dto.profiledto.ProfileIdx;
import me.gyun.ounce.dto.profiledto.ProfileRegister;
import me.gyun.ounce.mapper.ProfileMapper;
import me.gyun.ounce.model.DefaultRes;
import me.gyun.ounce.model.ProfileModel;
import me.gyun.ounce.utils.ResponseMessage;
import me.gyun.ounce.utils.StatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

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
     * @param ProfileMapper
     */
    public ProfileService(S3FileUploadService s3FileUploadService, ProfileMapper profileMapper, JwtService jwtService) {
        this.s3FileUploadService = s3FileUploadService;
        this.profileMapper = profileMapper;
        this.jwtService = jwtService;
    }

    /**
     * 고양이 프로필 등록
     *
     * @param ProfileModel
     */
    @Transactional
    public DefaultRes register(ProfileModel profileModel, int userIdx) {
        try {
            if (profileModel != null) {
                profileModel.setProfileURL(s3FileUploadService.upload(profileModel.getProfileImg()));
            }
            Profile profile = Profile.builder()
                    .profileName(profileModel.getProfileName())
                    .profileURL(profileModel.getProfileURL())
                    .profileCatWeight(profileModel.getProfileCatWeight())
                    .profileCatNeutral(profileModel.isProfileCatNeutral())
                    .profileCatAge(profileModel.getProfileCatAge())
                    .profileInfo(profileModel.getProfileInfo())
                    .build();

            ProfileRegister profileRegister = new ProfileRegister(profile, userIdx);
            profileMapper.profileRegister(profileRegister);
            ProfileIdx profileIdx = new ProfileIdx(profileRegister.getProfile().getProfileIdx());
            return DefaultRes.res(StatusCode.OK, ResponseMessage.PROFILE_REGISTER_SUCCESS, profileIdx);
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
    public DefaultRes update(ProfileModel profileModel, int userIdx, int profileIdx) {
        try {
            if (profileModel.getProfileImg() != null) {
                profileModel.setProfileURL(s3FileUploadService.upload(profileModel.getProfileImg()));
            }
            Profile profile = Profile.builder()
                    .profileName(profileModel.getProfileName())
                    .profileURL(profileModel.getProfileURL())
                    .profileCatWeight(profileModel.getProfileCatWeight())
                    .profileCatNeutral(profileModel.isProfileCatNeutral())
                    .profileCatAge(profileModel.getProfileCatAge())
                    .profileInfo(profileModel.getProfileInfo())
                    .build();

            if (profileMapper.isMyProfile(profileIdx, userIdx) > 0) {
                profileMapper.profileUpdate(profile, profileIdx);
                return DefaultRes.res(StatusCode.OK, ResponseMessage.PROFILE_UPDATE_SUCCESS);
            }
            return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.WRONG_VALUE);
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
     * @param token
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

    /**
     * 나의 프로필 전환
     *
     * @param userIdx
     * @param profileIdx
     */
    public DefaultRes conversion(int profileIdx, int userIdx) {
        try {
            List<ProfileConversion> profileList = profileMapper.profileConversion(profileIdx, userIdx);
            return DefaultRes.res(StatusCode.OK, ResponseMessage.PROFILE_CONVERSION_SUCCESS, profileList);
        } catch (Exception e) {
            log.error(e.getMessage());
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }
}
