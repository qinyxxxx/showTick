package com.qyx.showtick.common.entity;

import lombok.Getter;

/**
 * Created by Yuxin Qin on 7/25/24
 */
@Getter
public enum PaymentStatus {
    PENDING("Pending"),       // 待处理
    COMPLETED("Completed"),   // 已完成
    FAILED("Failed"),         // 失败
    REFUNDED("Refunded");     // 已退款

    private final String displayName;

    PaymentStatus(String displayName) {
        this.displayName = displayName;
    }
}
