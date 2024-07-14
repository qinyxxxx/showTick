package com.qyx.showtick.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * Created by Yuxin Qin on 7/14/24
 */
@Data
public class LoginParam {
    @NotEmpty
//    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    @NotEmpty
//    @ApiModelProperty(value = "密码", required = true)
    private String password;
}
