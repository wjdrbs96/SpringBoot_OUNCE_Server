package me.gyun.ounce.dto.user;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class SignUpDto {
    @NotEmpty
    private String id;
    @NotEmpty
    private String password;
    @Email
    private String email;
}
