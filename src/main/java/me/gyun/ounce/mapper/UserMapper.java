package me.gyun.ounce.mapper;

import me.gyun.ounce.dto.user.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    UserDto findByUserIdx(int userIdx);

    UserDto findByLoginId(String id);

    void signUp(UserDto userDto);

    void userUpdate(String refreshToken, int userIdx);
}
