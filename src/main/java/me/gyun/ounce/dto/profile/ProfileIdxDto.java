package me.gyun.ounce.dto.profile;

import lombok.Data;

@Data
public class ProfileIdxDto {
    private int profileIdx;

    public ProfileIdxDto(int profileIdx) {
        this.profileIdx = profileIdx;
    }
}
