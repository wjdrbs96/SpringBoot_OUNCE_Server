package me.gyun.ounce.mapper;

import me.gyun.ounce.dto.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO user(id, password, salt, email) VALUES(#{id}, #{password}, #{salt}, #{email})")
    @Options(useGeneratedKeys = true, keyColumn = "userIdx")
    int save(@Param("id") final String id, @Param("password") final String password, @Param("salt") final String salt, @Param("email") final String email);

    @Select("SELECT * FROM user WHERE id = #{id}")
    User findById(@Param("id") final String id);

    @Select("SELECT * FROM user WHERE userIdx = #{userIdx}")
    User findByUserIdx(@Param("userIdx") int userIdx);
}
