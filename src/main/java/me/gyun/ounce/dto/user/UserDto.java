package me.gyun.ounce.dto.user;

import lombok.Data;

@Data
public class UserDto {
    private int userIdx;
    private String id;
    private String password;
    private String email;
}
