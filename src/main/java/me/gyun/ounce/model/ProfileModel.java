package me.gyun.ounce.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProfileModel {
    private int profileIdx;
    private String profileName;
    private MultipartFile profileImg;
    private String profileURL;
    private double profileCatWeight;
    private boolean profileCatNeutral;
    private int profileCatAge;
    private String profileInfo;
}
