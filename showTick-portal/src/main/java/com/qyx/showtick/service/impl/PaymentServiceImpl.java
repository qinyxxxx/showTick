package com.qyx.showtick.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qyx.showtick.common.entity.*;
import com.qyx.showtick.common.mapper.OrderItemMapper;
import com.qyx.showtick.common.mapper.OrderMapper;
import com.qyx.showtick.common.mapper.PaymentMapper;
import com.qyx.showtick.common.mapper.TicketMapper;
import com.qyx.showtick.dto.CreatePaymentRequest;
import com.qyx.showtick.service.PaymentService;
import com.qyx.showtick.simplepay.dto.ShowTickNotificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Yuxin Qin on 7/26/24
 */
@Service
public class PaymentServiceImpl extends ServiceImpl<PaymentMapper, Payment>  implements PaymentService {

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private TicketMapper ticketMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    public Payment createPayment(Long orderId) {
        List<Payment> payments = paymentMapper.selectList(new QueryWrapper<Payment>().eq("order_id", orderId));
        if(!payments.isEmpty()){
            return payments.get(0);
        }
        Order order = orderMapper.selectById(orderId);
        if(order == null){
            throw new IllegalArgumentException("Order not found");
        }
        Payment payment = new Payment();
        payment.setPaymentMethod(PaymentMethod.UNKNOWN);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setAmount(order.getTotalAmount());
        payment.setOrderId(orderId);
        paymentMapper.insert(payment);

        return payment;
    }

    @Override
    public void handlePaymentCallBack(ShowTickNotificationRequest request) {

        // update payment
        Payment payment = paymentMapper.selectById(request.getPaymentId());
        payment.setStatus(request.getStatus());
        paymentMapper.updateById(payment);

        System.err.println(payment);

        // update order
        Order order = orderMapper.selectById(payment.getOrderId());
        if (request.getStatus() != PaymentStatus.COMPLETED){
            System.err.println("not completed");
            return;
        }

        System.err.println(order);

        order.setStatus(OrderStatus.COMPLETED);

        // update tickets
        List<OrderItem> orderItemList = orderItemMapper.selectList(new QueryWrapper<OrderItem>().eq("order_id", order.getId()));
        for(OrderItem orderItem : orderItemList){
            Ticket ticket = ticketMapper.selectById(orderItem.getTicketId());
            if(ticket.getStatus() != TicketStatus.LOCKED){
                throw new RuntimeException("ticket status error: " + ticket.getId() + " " + ticket.getStatus());
            }
            ticket.setStatus(TicketStatus.SOLD);
            System.err.println(ticket);
            break;
        }

    }
}
