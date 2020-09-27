package me.gyun.ounce.dto.profiledto;

import lombok.*;

@Data
@Builder
public class Profile {
    private int profileIdx;
    private String profileName;
    private String profileURL;
    private double profileCatWeight;
    private boolean profileCatNeutral;
    private int profileCatAge;
    private String profileInfo;
}
