package me.gyun.ounce.dto.user;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class SignInDto {
    @NotEmpty
    private String id;
    @NotEmpty
    private String password;
}
