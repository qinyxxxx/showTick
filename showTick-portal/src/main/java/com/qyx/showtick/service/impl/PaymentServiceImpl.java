package com.qyx.showtick.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qyx.showtick.common.entity.*;
import com.qyx.showtick.common.exception.Asserts;
import com.qyx.showtick.common.mapper.PaymentMapper;
import com.qyx.showtick.service.OrderService;
import com.qyx.showtick.service.PaymentService;
import com.qyx.showtick.service.TicketService;
import com.qyx.showtick.simplepay.dto.ShowTickNotificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Yuxin Qin on 7/26/24
 */
@Service
public class PaymentServiceImpl extends ServiceImpl<PaymentMapper, Payment>  implements PaymentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private OrderService orderService;

    @Autowired
    private TicketService ticketService;

    @Override
    public Payment createPayment(Long orderId) {
        List<Payment> payments = paymentMapper.selectList(new QueryWrapper<Payment>().eq("order_id", orderId));
        if(!payments.isEmpty()){
            return payments.get(0);
        }
        Order order = orderService.getById(orderId);
        if(order == null){
            LOGGER.error("Order not found");
            Asserts.fail("Order not found");
        }
        Payment payment = new Payment();
        payment.setPaymentMethod(PaymentMethod.UNKNOWN);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setAmount(order.getTotalAmount());
        payment.setOrderId(orderId);
        payment.setUserId(order.getUserId());
        paymentMapper.insert(payment);
        LOGGER.info("Create payment");
        return payment;
    }

    @Override
    public void handlePaymentCallBack(ShowTickNotificationRequest request) {
        LOGGER.info("Start callback");
        Order order = orderService.getById(request.getOrderId());

        // update payment
        Payment payment = paymentMapper.selectOne(new QueryWrapper<Payment>().eq("order_id", request.getOrderId()));
        if(payment == null){
            LOGGER.error("error, payment should not be null");
            Asserts.fail("error, payment should not be null");
        }
        payment.setStatus(request.getStatus());
        paymentMapper.updateById(payment);

        // update order
        if (request.getStatus() != PaymentStatus.COMPLETED){
            LOGGER.warn("not completed");
            return;
        }

        orderService.updateOrderStatus(order.getId(), OrderStatus.COMPLETED);
        LOGGER.info("Set order status to completed");
        // update tickets
        List<Ticket> tickets = orderService.getTicketsByOrderId(request.getOrderId());
        for(Ticket ticket : tickets){
            if(ticket.getStatus() != TicketStatus.LOCKED){
                LOGGER.error("ticket status error: " + ticket.getId() + " " + ticket.getStatus());
                Asserts.fail("ticket status error: " + ticket.getId() + " " + ticket.getStatus());
            }
            ticketService.updateTicketStatus(ticket.getId(), TicketStatus.SOLD);
            LOGGER.info("Set ticket {} status to sold", ticket.getId());
        }
    }

    @Override
    public Long getUserIdByPaymentId(Long paymentId) {
        Payment payment = paymentMapper.selectById(paymentId);
        if(payment == null){
            Asserts.fail("no payment found");
        }
        return payment.getUserId();
    }
}
