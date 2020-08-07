package me.gyun.ounce.dto;

import lombok.Data;

@Data
public class User {
    private int userIdx;
    private String id;
    private String password;
    private String email;
}
