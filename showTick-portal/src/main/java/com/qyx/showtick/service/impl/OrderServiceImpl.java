package com.qyx.showtick.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qyx.showtick.common.entity.*;
import com.qyx.showtick.common.mapper.*;
import com.qyx.showtick.dto.CreateOrderRequest;
import com.qyx.showtick.dto.OrderDTO;
import com.qyx.showtick.dto.OrderDetailResponse;
import com.qyx.showtick.service.OrderService;
import com.qyx.showtick.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private UserService userService;

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

        // get event info
        Event event = eventMapper.selectById(request.getEventId());
        if (event.getRemainingTicket() < request.getTicketIds().size()) {
            throw new RuntimeException("Not enough remaining tickets");
        }

        // 创建订单
        Order order = new Order();
        order.setUserId(userId);
        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.PENDING);
        order.setEventId(request.getEventId());
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

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        return orderItemMapper.selectList(new QueryWrapper<OrderItem>().eq("order_id", orderId));
    }

    @Override
    public List<Ticket> getTicketsByOrderId(Long orderId) {
        return getOrderItemsByOrderId(orderId).stream()
                .map(item -> ticketMapper.selectById(item.getTicketId()))
                .collect(Collectors.toList());
    }

    @Override
    public OrderDetailResponse getOrderDetailsByOrderId(Long orderId) {
        Order order = orderMapper.selectById(orderId);
        OrderDetailResponse response = new OrderDetailResponse();
        List<OrderItem> orderItems = orderItemMapper.selectList(new QueryWrapper<OrderItem>().eq("order_id", orderId));
        List<Ticket> tickets = getOrderItemsByOrderId(orderId).stream()
                .map(item -> ticketMapper.selectById(item.getTicketId()))
                .collect(Collectors.toList());
        if(tickets.isEmpty()){
            throw new RuntimeException("tickets should not be empty");
        }
        Event event = eventMapper.selectById(order.getEventId());
        response.setOrderItems(orderItems);
        response.setTickets(tickets);
        response.setTotalAmount(order.getTotalAmount());
        response.setOrderId(orderId);
        response.setStatus(order.getStatus());
        response.setEvent(event);
        return response;
    }

    @Override
    public int cancelOrder(Long orderId) {
        Order order = orderMapper.selectById(orderId);
        // update tickets status
        List<Ticket> tickets = getTicketsByOrderId(orderId);
        for(Ticket ticket : tickets){
            ticket.setStatus(TicketStatus.AVAILABLE);
            ticketMapper.updateById(ticket);
        }
        if(tickets.isEmpty()){
            throw new RuntimeException("tickets should not be empty!");
        }
        // update event remaining tickets(with lock)
        Event event = eventMapper.selectById(order.getEventId());
        event.setRemainingTicket(event.getRemainingTicket() + tickets.size());
        int affectedRows = eventMapper.updateWithOptimisticLock(event);
        if (affectedRows == 0) {
            throw new RuntimeException("Failed to update remaining tickets, please try again");
        }
        // update order status
        order.setStatus(OrderStatus.CANCELLED);
        int count = orderMapper.updateById(order);
        if(count != 1){
            throw new RuntimeException("Failed to update order status with cancel");
        }
        return count;
    }

    @Override
    public IPage<OrderDTO> getOrdersByUsername(String username, int pageNum, int pageSize) {
        User user = userService.geUserByUsername(username);

        // 获取分页的Order列表
        Page<Order> orderPage = new Page<>(pageNum, pageSize);
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getId());
        IPage<Order> ordersPageResult = orderMapper.selectPage(orderPage, queryWrapper);

        // 创建分页结果，包含OrderDTO列表
        Page<OrderDTO> orderDTOPage = new Page<>(pageNum, pageSize);
        List<OrderDTO> orderDTOList = ordersPageResult.getRecords().stream().map(order -> {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setOrder(order);
            // 获取Order对应的event
            orderDTO.setEvent(eventMapper.selectById(order.getEventId()));
            // 获取Order对应的tickets
            orderDTO.setTickets(getTicketsByOrderId(order.getId()));

            return orderDTO;
        }).collect(Collectors.toList());

        orderDTOPage.setRecords(orderDTOList);
        orderDTOPage.setTotal(ordersPageResult.getTotal());
        orderDTOPage.setCurrent(ordersPageResult.getCurrent());
        orderDTOPage.setSize(ordersPageResult.getSize());
        orderDTOPage.setPages(ordersPageResult.getPages());
        return orderDTOPage;
    }

    @Override
    public int updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderMapper.selectById(orderId);
        order.setStatus(status);
        int count = orderMapper.updateById(order);
        if(count != 1){
            throw new RuntimeException("update order status failed");
        }
        return count;
    }

}
