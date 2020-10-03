package me.gyun.ounce.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.gyun.ounce.dto.ResponseDto;
import me.gyun.ounce.dto.profile.ProfileIdxDto;
import me.gyun.ounce.dto.profile.ProfileRegisterDto;
import me.gyun.ounce.mapper.ProfileMapper;
import me.gyun.ounce.utils.ResponseMessage;
import me.gyun.ounce.utils.StatusCode;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileMapper profileMapper;
    private final S3FileUploadService s3FileUploadService;
    private final JwtService jwtService;

    /**
     * 프로필 등록
     * @param accessToken
     * @param ProfileRegisterDto
     */
    public ResponseDto<?> profileRegister(ProfileRegisterDto profileRegisterDto, String accessToken) {
        try {
            String uploadURL = s3FileUploadService.upload(profileRegisterDto.getProfileImg());
            profileRegisterDto.setProfileURL(uploadURL);
            JwtService.TOKEN decode = jwtService.decode(accessToken);
            profileMapper.profileRegister(profileRegisterDto, decode.getUserIdx());
            ProfileIdxDto profileIdxDto = new ProfileIdxDto(profileRegisterDto.getProfileIdx());
            return ResponseDto.res(StatusCode.OK, ResponseMessage.PROFILE_REGISTER, profileIdxDto);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseDto.res(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);
        }
    }
}
