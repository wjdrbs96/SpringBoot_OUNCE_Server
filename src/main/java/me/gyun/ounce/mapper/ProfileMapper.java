package me.gyun.ounce.mapper;

import me.gyun.ounce.dto.Profile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProfileMapper {

    // 프로필 등록
    int profileRegister(@Param("profile") Profile profile, @Param("userIdx") int userIdx);
}
