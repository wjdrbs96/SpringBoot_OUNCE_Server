package me.gyun.ounce.dto.profiledto;

import lombok.Data;

@Data
public class ProfileConversionDto {
    private int profileIdx;
    private String profileURL;
    private String profileName;
    private String profileInfo;
}
