package me.gyun.ounce.dto.logindto;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.gyun.ounce.dto.profiledto.ProfileCountDto;

@Data
@AllArgsConstructor
public class LoginResultDto {
    private String token;
    private ProfileCountDto profileCount;
}
