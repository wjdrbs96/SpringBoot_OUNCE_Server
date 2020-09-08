package me.gyun.ounce.mapper;

import me.gyun.ounce.dto.Profile;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProfileMapper {

    // 프로필 등록
    int profileRegister(Profile profile);
}
