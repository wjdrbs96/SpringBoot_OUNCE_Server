package me.gyun.ounce.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class SignInModel {
    @NotEmpty
    private String id;
    @NotEmpty
    private String password;
}
