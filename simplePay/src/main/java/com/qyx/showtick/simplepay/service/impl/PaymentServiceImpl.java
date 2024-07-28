package com.qyx.showtick.simplepay.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qyx.showtick.simplepay.dto.CreatePaymentRequest;
import com.qyx.showtick.simplepay.dto.PaymentResponse;
import com.qyx.showtick.simplepay.entity.Payment;
import com.qyx.showtick.simplepay.entity.PaymentTransaction;
import com.qyx.showtick.simplepay.mapper.PaymentMapper;
import com.qyx.showtick.simplepay.mapper.PaymentTransactionMapper;
import com.qyx.showtick.simplepay.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Yuxin Qin on 7/28/24
 */
@Service
public class PaymentServiceImpl extends ServiceImpl<PaymentMapper, Payment> implements PaymentService {
    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private PaymentTransactionMapper paymentTransactionMapper;

    @Override
    public Payment createPayment(CreatePaymentRequest request) {
        Payment payment = new Payment();
        payment.setOrderId(request.getOrderId());
        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setPaymentStatus("Pending");
        paymentMapper.insert(payment);
        return payment;
    }

    @Override
    public void updatePaymentStatus(Long paymentId, String status) {
        Payment payment = paymentMapper.selectById(paymentId);
        if (payment != null) {
            payment.setPaymentStatus(status);
            paymentMapper.updateById(payment);
        }
    }

    @Override
    public void recordTransaction(PaymentTransaction transaction) {
        paymentTransactionMapper.insert(transaction);
    }

    @Override
    public PaymentResponse getPaymentById(Long paymentId) {
//        Payment payment = paymentMapper.selectById(paymentId);
//        if (payment != null) {
//            PaymentResponse response = new PaymentResponse(payment);
//            response.setId(payment.getId());
//            response.setOrderId(payment.getOrderId());
//            response.setAmount(payment.getAmount());
//            response.setPaymentMethod(payment.getPaymentMethod());
//            response.setStatus(payment.getPaymentStatus());
//            return response;
//        }
        return null;
    }
}
