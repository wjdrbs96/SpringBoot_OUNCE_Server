package me.gyun.ounce.dto.logindto;

import lombok.Data;

@Data
public class UserProfile {
    private String id;
    private int profileIdx;
    private String profileURL;
    private String profileName;
    private String profileInfo;
}
