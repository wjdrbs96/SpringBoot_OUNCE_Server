package me.gyun.ounce.mapper;

import me.gyun.ounce.dto.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO user(id, password, email) VALUES(#{id}, #{password}, #{email})")
    @Options(useGeneratedKeys = true, keyColumn = "userIdx")
    int save(@Param("id") final String id, @Param("password") final String password, @Param("email") final String email);

    @Select("SELECT * FROM user WHERE id = #{id} and #{password}")
    User findByIdAndPassword(@Param("id") final String id, @Param("password") final String password);

    @Select("SELECT * FROM user WHERE id = #{id}")
    User findById(@Param("id") final String id);

    @Select("SELECT * FROM user WHERE userIdx = #{userIdx}")
    User findByUserIdx(@Param("userIdx") int userIdx);
}
