package com.qyx.showtickcommon.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Yuxin Qin
 * @version v1.0
 * @date 7/9/24
 */
@Data
@TableName("user")
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String username;
    private String email;
    private Integer gender;

    private String passwordHash;
    private String mobile;
    private String address;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
