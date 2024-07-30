package com.qyx.showtick.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.qyx.showtick.common.entity.*;
import com.qyx.showtick.dto.CreateOrderRequest;
import com.qyx.showtick.dto.OrderDetailResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Yuxin Qin on 7/25/24
 */

public interface OrderService extends IService<Order> {
    @Transactional
    Order createOrder(CreateOrderRequest request, Long userId);


    List<OrderItem> getOrderItemsByOrderId(Long orderId);

    List<Ticket> getTicketsByOrderId(Long orderId);

    OrderDetailResponse getOrderDetailsByOrderId(Long orderId);

    @Transactional
    int cancelOrder(Long orderId);

}
