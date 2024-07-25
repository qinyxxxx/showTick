package com.qyx.showtick.service.impl;

import com.qyx.showtick.common.entity.*;
import com.qyx.showtick.common.mapper.OrderItemMapper;
import com.qyx.showtick.common.mapper.OrderMapper;
import com.qyx.showtick.common.mapper.TicketMapper;
import com.qyx.showtick.dto.CreateOrderRequest;
import com.qyx.showtick.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Yuxin Qin on 7/25/24
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private TicketMapper ticketMapper;


    @Override
    public Order createOrder(CreateOrderRequest request) {
        float totalAmount = 0.0f;

        List<Ticket> tickets = ticketMapper.selectBatchIds(request.getTicketIds());
        for (Ticket ticket : tickets) {
            if(ticket.getStatus() != TicketStatus.AVAILABLE) {
                throw new RuntimeException("Ticket " + ticket.getId() + " is not available");
            }
            ticket.setStatus(TicketStatus.RESERVED);
            ticketMapper.updateById(ticket);
            totalAmount += ticket.getPrice();
        }

        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.PENDING);
        orderMapper.insert(order);

        for(Ticket ticket : tickets){
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setAmount(ticket.getPrice());
            orderItem.setTicketId(ticket.getId());
            orderItemMapper.insert(orderItem);
        }

        return order;
    }
}
