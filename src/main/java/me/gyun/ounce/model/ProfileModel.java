package me.gyun.ounce.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ProfileModel {
    @NotEmpty
    private String profileName;
    @NotNull
    private double profileCatWeight;
    private MultipartFile profileImg;
    private String profileURL;
    @NotNull
    private boolean profileCatNeutral;
    @NotNull
    private int profileCatAge;
    @NotEmpty
    private String profileInfo;
}
