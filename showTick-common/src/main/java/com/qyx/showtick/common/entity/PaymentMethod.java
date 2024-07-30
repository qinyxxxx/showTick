package com.qyx.showtick.common.entity;

import lombok.Getter;

/**
 * Created by Yuxin Qin on 7/29/24
 */
@Getter
public enum PaymentMethod {
    CREDIT_CARD("Credit Card"),
    PAYPAL("Paypal"),
    ALIPAY("Alipay"),
    UNKNOWN("Not Set");

    private final String displayName;

    PaymentMethod(String displayName) {
        this.displayName = displayName;
    }
}
