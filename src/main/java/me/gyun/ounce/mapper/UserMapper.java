package me.gyun.ounce.mapper;

import me.gyun.ounce.dto.user.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    UserDto findByUserIdx(int userIdx);
}
