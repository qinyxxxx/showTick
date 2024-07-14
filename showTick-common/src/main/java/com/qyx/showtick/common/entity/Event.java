package com.qyx.showtick.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by Yuxin Qin on 7/9/24
 */
@Data
@TableName("event")
public class Event implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;
    private float price;
    private String performer;
    private int status;
    private Long categoryId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
