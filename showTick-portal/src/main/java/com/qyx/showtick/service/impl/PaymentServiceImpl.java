package com.qyx.showtick.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qyx.showtick.common.entity.Order;
import com.qyx.showtick.common.entity.Payment;
import com.qyx.showtick.common.entity.PaymentStatus;
import com.qyx.showtick.common.mapper.OrderMapper;
import com.qyx.showtick.common.mapper.PaymentMapper;
import com.qyx.showtick.dto.CreatePaymentRequest;
import com.qyx.showtick.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Yuxin Qin on 7/26/24
 */
@Service
public class PaymentServiceImpl extends ServiceImpl<PaymentMapper, Payment>  implements PaymentService {

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Payment createPayment(CreatePaymentRequest request) {
        Order order = orderMapper.selectById(request.getOrderId());
        if(order == null){
            throw new IllegalArgumentException("Order not found");
        }
        Payment payment = new Payment();
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setAmount(order.getTotalAmount());
        payment.setOrderId(request.getOrderId());
        paymentMapper.insert(payment);

        return payment;
    }
}
