package com.qyx.showtick.dto;

import com.qyx.showtick.common.entity.Event;
import com.qyx.showtick.common.entity.Order;
import com.qyx.showtick.common.entity.Ticket;
import lombok.Data;

import java.util.List;

/**
 * Created by Yuxin Qin on 7/31/24
 */
@Data
public class OrderDTO {
    private Order order;
    private List<Ticket> tickets;
    private Event event;
}
