package me.gyun.ounce.mapper;

import me.gyun.ounce.dto.profiledto.ProfileCountDto;
import me.gyun.ounce.dto.logindto.UserDto;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    // 회원가입
    int userInsert(@Param("user") UserDto user);
    // ID로 유저 찾기
    UserDto findById(@Param("id") final String id);
    // userIdx로 유저 찾기
    UserDto findByUserIdx(@Param("userIdx") int userIdx);
    // 중복아이디 체크
    int checkByLoginId(@Param("id") String id);
    // 유저의 프로필 수
    // return 타입이 객체 or List 라면 setter를 통해서 SQL을 객체에 맵핑하고 객체를 만들어야 하기 때문에 default Construct가 필요하지 않나 라는 생각
    ProfileCountDto userProfileCount(@Param("userIdx") int userIdx);
}
