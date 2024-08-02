package com.qyx.showtick.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qyx.showtick.common.entity.*;
import com.qyx.showtick.common.exception.Asserts;
import com.qyx.showtick.common.mapper.*;
import com.qyx.showtick.dto.CreateOrderRequest;
import com.qyx.showtick.dto.OrderDTO;
import com.qyx.showtick.dto.OrderDetailResponse;
import com.qyx.showtick.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Yuxin Qin on 7/25/24
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

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
        LOGGER.info("Start create order: {}", request.toString());
        if(request.getTicketIds().isEmpty()){
            LOGGER.warn("No tickets to create");
            return null;
        }
        float totalAmount = 0.0f;
        List<Ticket> tickets = ticketMapper.selectBatchIds(request.getTicketIds());
        // lock the tickets
        for (Ticket ticket : tickets) {
            if (ticket == null || ticket.getStatus() != TicketStatus.AVAILABLE) {
                LOGGER.error("Ticket is null or is not available");
                Asserts.fail("Ticket is null or is not available");
            }
            totalAmount += ticket.getPrice();
            ticket.setStatus(TicketStatus.LOCKED);
            ticketMapper.updateById(ticket);
            LOGGER.info("Locked ticket {}", ticket.getId());
        }

        LOGGER.info("Finish locking tickets");

        // get event info
        Event event = eventMapper.selectById(request.getEventId());
        if (event.getRemainingTicket() < request.getTicketIds().size()) {
            LOGGER.error("Not enough remaining tickets{} for event {}", request.getTicketIds().size(), event.getId());
            Asserts.fail("Not enough remaining tickets");
        }

        LOGGER.info("Event {} has enough remaining tickets", event.getId());

        // 创建订单
        Order order = new Order();
        order.setUserId(userId);
        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.PENDING);
        order.setEventId(request.getEventId());
        orderMapper.insert(order);
        LOGGER.info("Create order: {}", order);

        // 更新剩余票数和版本号
        event.setRemainingTicket(event.getRemainingTicket() - request.getTicketIds().size());
        int affectedRows = eventMapper.updateWithOptimisticLock(event);
        if (affectedRows == 0) {
            LOGGER.error("Failed to update remaining tickets, please try again");
            Asserts.fail("Failed to update remaining tickets, please try again");
        }
        LOGGER.info("Update remaining tickets");

        for(Ticket ticket : tickets){
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setAmount(ticket.getPrice());
            orderItem.setTicketId(ticket.getId());
            orderItem.setUserId(userId);
            orderItemMapper.insert(orderItem);
            LOGGER.info("Insert orderItem: {}", orderItem);
        }

        LOGGER.info("Finish create order");
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
        LOGGER.info("Get orderItems");
        List<Ticket> tickets = getOrderItemsByOrderId(orderId).stream()
                .map(item -> ticketMapper.selectById(item.getTicketId()))
                .collect(Collectors.toList());
        LOGGER.info("Get tickets");
        response.setOrderItems(orderItems);
        response.setTickets(tickets);
        response.setTotalAmount(order.getTotalAmount());
        response.setOrderId(orderId);
        response.setStatus(order.getStatus());
        response.setEvent(eventMapper.selectById(order.getEventId()));
        return response;
    }

    @Override
    public int cancelOrder(Long orderId) {
        Order order = orderMapper.selectById(orderId);
        if(order.getStatus() != OrderStatus.PENDING){
            LOGGER.warn("order is finished");
            return 0;
        }
        LOGGER.info("Start canceling order: {}", orderId);
        // update tickets status
        List<Ticket> tickets = getTicketsByOrderId(orderId);
        for(Ticket ticket : tickets){
            ticket.setStatus(TicketStatus.AVAILABLE);
            ticketMapper.updateById(ticket);
        }
        LOGGER.info("Update tickets to available");
        // update event remaining tickets(with lock)
        Event event = eventMapper.selectById(order.getEventId());
        LOGGER.info("Event {} remaining ticket before: {}", event.getId(), event.getRemainingTicket());
        event.setRemainingTicket(event.getRemainingTicket() + tickets.size());
        int affectedRows = eventMapper.updateWithOptimisticLock(event);
        if (affectedRows == 0) {
            LOGGER.error("Failed to update remaining tickets");
            Asserts.fail("Failed to update remaining tickets");
        }
        LOGGER.info("Event {} remaining ticket after: {}", event.getId(), event.getRemainingTicket());
        // update order status
        order.setStatus(OrderStatus.CANCELLED);
        int count = orderMapper.updateById(order);
        LOGGER.info("Update order {} status to cancelled", order.getId());
        if(count != 1){
            LOGGER.error("Failed to update order status with cancel");
            Asserts.fail("Failed to update order status with cancel");
        }
        LOGGER.info("Finish canceling order");
        return count;
    }

    @Override
    public IPage<OrderDTO> getOrdersByUsername(Long userId, int pageNum, int pageSize) {

        // 获取分页的Order列表
        Page<Order> orderPage = new Page<>(pageNum, pageSize);
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
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
            Asserts.fail("update order status failed");
        }
        return count;
    }

    @Override
    public Long getUserIdByOrderId(Long orderId) {
        Order order = orderMapper.selectById(orderId);
        if(order == null){
            Asserts.fail("order not found");
        }
        return order.getUserId();
    }

    @Override
    public int cancelTimeoutOrders() {
        List<Order> timeoutOrders = getTimeOutOrders();
        if(CollectionUtils.isEmpty(timeoutOrders)){
            return 0;
        }
        for (Order timeoutOrder : timeoutOrders) {
            // update tickets status
            List<Ticket> tickets = getTicketsByOrderId(timeoutOrder.getId());
            for(Ticket ticket : tickets){
                ticket.setStatus(TicketStatus.AVAILABLE);
                ticketMapper.updateById(ticket);
            }
            LOGGER.info("Update tickets to available");
            // update event remaining tickets(with lock)
            Event event = eventMapper.selectById(timeoutOrder.getEventId());
            LOGGER.info("Event {} remaining ticket before: {}", event.getId(), event.getRemainingTicket());
            event.setRemainingTicket(event.getRemainingTicket() + tickets.size());
            int affectedRows = eventMapper.updateWithOptimisticLock(event);
            if (affectedRows == 0) {
                LOGGER.error("Failed to update remaining tickets");
                Asserts.fail("Failed to update remaining tickets");
            }
            LOGGER.info("Event {} remaining ticket after: {}", event.getId(), event.getRemainingTicket());
            // update order status
            timeoutOrder.setStatus(OrderStatus.CANCELLED);
            orderMapper.updateById(timeoutOrder);
            LOGGER.info("Update order {} status to cancelled", timeoutOrder.getId());
            LOGGER.info("Finish canceling order");
        }
        return timeoutOrders.size();
    }

    @Override
    public void updateTicketsStatusByOrderId(Long orderId, TicketStatus status) {
        List<Ticket> tickets = getTicketsByOrderId(orderId);
        for(Ticket ticket : tickets){
            ticket.setStatus(status);
            ticketMapper.updateById(ticket);
        }
    }

    public List<Order> getTimeOutOrders(){
        // 获取当前时间并减去10分钟
        LocalDateTime tenMinutesAgo = LocalDateTime.now().minusMinutes(10);

        // 查询创建时间超过十分钟且未支付的订单
        return orderMapper.selectList(new QueryWrapper<Order>()
                .le("create_time", tenMinutesAgo)
                .eq("status", OrderStatus.PENDING));
    }

}
