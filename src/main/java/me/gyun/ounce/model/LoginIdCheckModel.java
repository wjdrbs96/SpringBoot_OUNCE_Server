package me.gyun.ounce.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginIdCheckModel {
    @NotEmpty
    String id;
}