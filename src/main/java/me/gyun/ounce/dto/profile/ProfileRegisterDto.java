package me.gyun.ounce.dto.profile;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

@Data
public class ProfileRegisterDto {
    private int profileIdx;
    @NotEmpty
    private String profileName;
    private String profileURL;
    private MultipartFile profileImg;
    private double profileCatWeight;
    private boolean profileCatNeutral;
    private int profileCatAge;
    @NotEmpty
    private String profileInfo;
}
