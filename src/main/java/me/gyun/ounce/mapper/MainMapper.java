package me.gyun.ounce.mapper;

import me.gyun.ounce.dto.MainProfile;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MainMapper {

    // 메인 화면 상단 조회
    MainProfile MainProfileInquiry(int profileIdx);
}
