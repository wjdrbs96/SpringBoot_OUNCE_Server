package me.gyun.ounce.mapper;

import me.gyun.ounce.dto.logindto.UserProfileDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SearchMapper {

    // 유저검색
    List<UserProfileDto> userSearch(String Id);
}
