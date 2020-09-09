package me.gyun.ounce.mapper;

import me.gyun.ounce.dto.User;
import me.gyun.ounce.dto.UserProfile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SearchMapper {

    // 유저검색
    List<UserProfile> userSearch(String Id);
}
