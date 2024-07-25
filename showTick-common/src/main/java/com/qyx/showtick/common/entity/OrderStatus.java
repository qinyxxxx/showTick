package com.qyx.showtick.common.entity;

import lombok.Getter;

/**
 * Created by Yuxin Qin on 7/25/24
 */
@Getter
public enum OrderStatus {
    PENDING("Pending"),       // 待处理
    CONFIRMED("Confirmed"),   // 已确认
    CANCELLED("Cancelled"),   // 已取消
    COMPLETED("Completed");   // 已完成

    private final String displayName;

    OrderStatus(String displayName) {
        this.displayName = displayName;
    }
}
