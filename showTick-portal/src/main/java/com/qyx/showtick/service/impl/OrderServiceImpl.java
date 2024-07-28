package com.qyx.showtick.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qyx.showtick.common.entity.*;
import com.qyx.showtick.common.mapper.*;
import com.qyx.showtick.dto.CreateOrderRequest;
import com.qyx.showtick.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Yuxin Qin on 7/25/24
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>  implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private TicketMapper ticketMapper;

    @Autowired
    private EventMapper eventMapper;

    @Override
    public Order createOrder(CreateOrderRequest request, Long userId) {
        float totalAmount = 0.0f;
        List<Ticket> tickets = ticketMapper.selectBatchIds(request.getTicketIds());

        // lock the tickets
        for (Ticket ticket : tickets) {
            if (ticket == null || ticket.getStatus() != TicketStatus.AVAILABLE) {
                throw new IllegalArgumentException("Ticket not available");
            }
            totalAmount += ticket.getPrice();
            ticket.setStatus(TicketStatus.LOCKED);
            ticketMapper.updateById(ticket);
        }

        // 获取事件信息
        Event event = eventMapper.selectById(tickets.get(0).getEventId());
        if (event.getRemainingTicket() < request.getTicketIds().size()) {
            throw new RuntimeException("Not enough remaining tickets");
        }

        // 创建订单
        Order order = new Order();
        order.setUserId(userId);
        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.PENDING);
        orderMapper.insert(order);

        // 更新剩余票数和版本号
        event.setRemainingTicket(event.getRemainingTicket() - request.getTicketIds().size());
        int affectedRows = eventMapper.updateWithOptimisticLock(event);
        if (affectedRows == 0) {
            throw new RuntimeException("Failed to update remaining tickets, please try again");
        }

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
