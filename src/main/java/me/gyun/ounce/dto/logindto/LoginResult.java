package me.gyun.ounce.dto.logindto;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.gyun.ounce.dto.profiledto.ProfileCount;

@Data
@AllArgsConstructor
public class LoginResult {
    private String token;
    private ProfileCount profileCount;
}
