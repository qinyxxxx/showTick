package com.qyx.showtick.dto;

import lombok.Data;

/**
 * Created by Yuxin Qin on 7/15/24
 */
@Data
public class LoginResponse {
    private String token;

    public LoginResponse(String token) {
        this.token = token;
    }
}
