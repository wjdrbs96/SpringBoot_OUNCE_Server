package me.gyun.ounce.mapper;

import me.gyun.ounce.dto.maindto.MainProfileDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MainMapper {

    // 메인 화면 상단 조회
    MainProfileDto MainProfileInquiry(int profileIdx);
}
