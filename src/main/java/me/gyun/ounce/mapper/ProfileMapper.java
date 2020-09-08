package me.gyun.ounce.mapper;

import me.gyun.ounce.dto.Profile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProfileMapper {

    // 프로필 등록
    int profileRegister(@Param("profile") Profile profile, @Param("userIdx") int userIdx);

    // 프로필 개수 제한
    int profileRegisterLimit(@Param("userIdx") int userIdx);

    // 프로필 수정
    void profileUpdate(@Param("profile") Profile profile, @Param("userIdx") int userIdx);

    // 프로필 수정권한 체크
    int isMyProfile(@Param("profileIdx") int profileIdx, @Param("userIdx") int userIdx);
}
