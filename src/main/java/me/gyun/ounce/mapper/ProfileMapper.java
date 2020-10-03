package me.gyun.ounce.mapper;

import me.gyun.ounce.dto.profile.ProfileRegisterDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProfileMapper {

    // 프로필 등록
    void profileRegister(ProfileRegisterDto profileRegisterDto, int userIdx);
}
