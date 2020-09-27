package me.gyun.ounce.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class SignUpModel {
    @NotEmpty
    private String id;
    @NotEmpty
    private String password;
    @Email
    private String email;
}
