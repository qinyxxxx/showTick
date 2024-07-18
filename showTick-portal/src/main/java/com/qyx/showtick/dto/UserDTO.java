package com.qyx.showtick.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Yuxin Qin on 7/18/24
 */
@Data
@EqualsAndHashCode
public class UserDTO {
    private String username;
    private String gender;
    private String email;
    private String mobile;
    private String address;
}
