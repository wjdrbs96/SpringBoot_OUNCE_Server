package me.gyun.ounce.dto.profiledto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileRegisterDto {
    private ProfileDto profile;
    private int userIdx;
}
