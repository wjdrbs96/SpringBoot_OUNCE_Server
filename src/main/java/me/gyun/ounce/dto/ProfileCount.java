package me.gyun.ounce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCount {
    private int profileIdx;
    private int userProfileCount;
}
