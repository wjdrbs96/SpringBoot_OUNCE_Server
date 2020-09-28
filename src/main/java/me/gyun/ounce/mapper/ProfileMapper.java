package me.gyun.ounce.mapper;

import me.gyun.ounce.dto.profiledto.ProfileDto;
import me.gyun.ounce.dto.profiledto.ProfileConversionDto;
import me.gyun.ounce.dto.profiledto.ProfileRegisterDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProfileMapper {

    // 프로필 등록
    int profileRegister(@Param("profileRegister") ProfileRegisterDto profileRegister);

    // 프로필 개수 제한
    int profileRegisterLimit(@Param("userIdx") int userIdx);

    // 프로필 수정
    void profileUpdate(@Param("profile") ProfileDto profile, @Param("profileIdx") int profileIdx);

    // 프로필 수정권한 체크
    int isMyProfile(@Param("profileIdx") int profileIdx, @Param("userIdx") int userIdx);

    // 나의 프로필 계정 전환
    List<ProfileConversionDto> profileConversion(@Param("profileIdx") int profileIdx, @Param("userIdx") int userIdx);

    // 팔로우 취소
    void followDelete(int followerIdx, int followingIdx);
}
