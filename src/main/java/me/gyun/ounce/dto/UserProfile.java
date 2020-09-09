package me.gyun.ounce.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserProfile {
    private int userIdx;
    private String id;
    private int profileIdx;
    private String profileURL;
    private String profileName;
    private String profileInfo;
}
