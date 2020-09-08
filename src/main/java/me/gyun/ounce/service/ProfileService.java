package me.gyun.ounce.service;

import lombok.extern.slf4j.Slf4j;
import me.gyun.ounce.dto.Profile;
import me.gyun.ounce.mapper.ProfileMapper;
import me.gyun.ounce.model.DefaultRes;
import me.gyun.ounce.model.ProfileModel;
import me.gyun.ounce.utils.ResponseMessage;
import me.gyun.ounce.utils.StatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Slf4j
@Service
public class ProfileService {

    private final S3FileUploadService s3FileUploadService;
    private final ProfileMapper profileMapper;

    /**
     * 생성자 의존성 주입
     *
     * @param S3FileUploadService
     * @Param ProfileMapper
     */
    public ProfileService(S3FileUploadService s3FileUploadService, ProfileMapper profileMapper) {
        this.s3FileUploadService = s3FileUploadService;
        this.profileMapper = profileMapper;
    }

    /**
     * 고양이 프로필 등록
     *
     * @Param ProfileModel
     */
    @Transactional
    public DefaultRes register(ProfileModel profileModel) {
        try {
            if (profileModel != null) {
                profileModel.setProfileURL(s3FileUploadService.upload(profileModel.getProfileImg()));
            }
            Profile profile = new Profile(profileModel.getProfileName(), profileModel.getProfileURL(),
                                          profileModel.getProfileCatWeight(), profileModel.isProfileCatNeutral(),
                                          profileModel.getProfileCatAge(), profileModel.getProfileInfo());

            profileMapper.profileRegister(profile);
            return DefaultRes.res(StatusCode.OK, ResponseMessage.PROFILE_REGISTER_SUCCESS, profileModel.getProfileIdx());
        } catch (Exception e) {
            //Rollback
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(e.getMessage());
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }
}
