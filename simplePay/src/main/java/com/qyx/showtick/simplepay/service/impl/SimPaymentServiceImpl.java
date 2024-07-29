package com.qyx.showtick.simplepay.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qyx.showtick.common.dto.SimPayResponse;
import com.qyx.showtick.common.dto.SimplePayRequest;
import com.qyx.showtick.common.entity.PaymentStatus;
import com.qyx.showtick.common.entity.SimPayment;
import com.qyx.showtick.common.entity.SimPaymentTransaction;
import com.qyx.showtick.common.mapper.SimPaymentMapper;
import com.qyx.showtick.common.mapper.PaymentTransactionMapper;
import com.qyx.showtick.simplepay.service.SimPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Yuxin Qin on 7/28/24
 */
@Service
public class SimPaymentServiceImpl extends ServiceImpl<SimPaymentMapper, SimPayment> implements SimPaymentService {
    @Autowired
    private SimPaymentMapper simPaymentMapper;

    @Autowired
    private PaymentTransactionMapper paymentTransactionMapper;

    @Override
    public SimPayResponse createPayment(SimplePayRequest request) {
        System.err.println(request.toString());
        SimPayment payment = new SimPayment();
        payment.setOrderId(request.getOrderId());
        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setStatus(PaymentStatus.PENDING);
        simPaymentMapper.insert(payment);
        SimPayResponse response = new SimPayResponse();
        response.setStatus(payment.getStatus());
        response.setPaymentMethod(payment.getPaymentMethod());
        response.setPayLink("www.baidu.com");
        response.setAmount(payment.getAmount());
        response.setOrderId(payment.getOrderId());
        return response;
    }

    @Override
    public void updatePaymentStatus(Long paymentId, PaymentStatus status) {
        SimPayment payment = simPaymentMapper.selectById(paymentId);
        if (payment != null) {
            payment.setStatus(status);
            simPaymentMapper.updateById(payment);
        }
    }

    @Override
    public void recordTransaction(SimPaymentTransaction transaction) {
        paymentTransactionMapper.insert(transaction);
    }

    @Override
    public SimPayResponse getPaymentById(Long paymentId) {
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
