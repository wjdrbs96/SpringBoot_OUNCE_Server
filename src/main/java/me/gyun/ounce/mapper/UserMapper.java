package me.gyun.ounce.mapper;

import me.gyun.ounce.dto.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO user(id, password, salt, email) VALUES(#{id}, #{password}, #{salt}, #{email})")
    void save(@Param("id") final String id, @Param("password") final String password, @Param("salt") final String salt, @Param("email") final String email);

    @Select("SELECT * FROM user WHERE id = #{id} AND password = #{password}")
    User findByIdAndPassword(@Param("id") final String id, @Param("password") final String password);

    @Select("SELECT * FROM user WHERE userIdx = #{userIdx}")
    User findByUserIdx(@Param("userIdx") int userIdx);
}
