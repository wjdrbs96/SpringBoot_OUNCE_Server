package me.gyun.ounce.dto;

import lombok.Data;

@Data
public class Profile {
    private int profileIdx;
    private String profileName;
    private String profileURL;
    private double profileCatWeight;
    private boolean profileCatNeutral;
    private int profileCatAge;
    private String profileInfo;

    public Profile(String profileName, String profileURL, double profileCatWeight, boolean profileCatNeutral, int profileCatAge, String profileInfo) {
        this.profileName = profileName;
        this.profileURL = profileURL;
        this.profileCatWeight = profileCatWeight;
        this.profileCatNeutral = profileCatNeutral;
        this.profileCatAge = profileCatAge;
        this.profileInfo = profileInfo;
    }
}
