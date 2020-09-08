package me.gyun.ounce.mapper;

import me.gyun.ounce.dto.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    // 회원가입
    int userInsert(User user);
    // ID로 유저 찾기
    User findById(@Param("id") final String id);
    // userIdx로 유저 찾기
    User findByUserIdx(@Param("userIdx") int userIdx);
}
