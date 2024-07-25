package com.qyx.showtick.common.entity;

import lombok.Getter;

/**
 * Created by Yuxin Qin on 7/25/24
 */
@Getter
public enum TicketStatus {
    AVAILABLE(0),
    SOLD(1),
    RESERVED(2);

    private final int code;

    TicketStatus(int code) {
        this.code = code;
    }
}
