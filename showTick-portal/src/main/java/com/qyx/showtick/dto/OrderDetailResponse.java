package com.qyx.showtick.dto;

import com.qyx.showtick.common.entity.Event;
import com.qyx.showtick.common.entity.OrderItem;
import com.qyx.showtick.common.entity.OrderStatus;
import com.qyx.showtick.common.entity.Ticket;
import lombok.Data;

import java.util.List;

/**
 * Created by Yuxin Qin on 7/29/24
 */
@Data
public class OrderDetailResponse {
    private Long orderId;
    private List<OrderItem> orderItems;
    private float totalAmount;
    private List<Ticket> tickets;
    private OrderStatus status;
    private Event event;
}
