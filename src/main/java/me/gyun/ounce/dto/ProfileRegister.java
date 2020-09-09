package me.gyun.ounce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileRegister {
    private Profile profile;
    private int userIdx;
}
