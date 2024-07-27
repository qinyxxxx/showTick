package com.qyx.showtick.common.entity;

import lombok.Getter;

/**
 * Created by Yuxin Qin on 7/25/24
 */
@Getter
public enum TicketStatus {
    AVAILABLE("Available"),
    SOLD("Sold"),
    LOCKED("Locked");

    private final String displayName;

    TicketStatus(String displayName) {
        this.displayName = displayName;
    }
}
