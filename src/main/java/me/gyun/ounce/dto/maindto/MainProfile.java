package me.gyun.ounce.dto.maindto;

import lombok.Data;

@Data
public class MainProfile {
    private String profileName;
    private String profileURL;
    private double profileCatWeight;
    private boolean profileCatNeutral;
    private int profileCatAge;
    private String profileInfo;
    private int myFollowingCount;
    private int myFollowerCount;
}
