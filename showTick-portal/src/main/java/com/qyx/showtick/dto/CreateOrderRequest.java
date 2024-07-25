package com.qyx.showtick.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by Yuxin Qin on 7/25/24
 */
@Data
public class CreateOrderRequest {
    private Long userId;
    private Long eventId;
    private List<Long> ticketIds;
}
