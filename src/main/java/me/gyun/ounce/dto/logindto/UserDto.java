package me.gyun.ounce.dto.logindto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private int userIdx;
    private String id;
    private String password;
    private String email;

    public UserDto(String id, String password, String email) {
        this.id = id;
        this.password = password;
        this.email = email;
    }
}
