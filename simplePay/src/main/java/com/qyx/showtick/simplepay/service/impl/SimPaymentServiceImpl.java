package com.qyx.showtick.simplepay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qyx.showtick.common.dto.SimPayCreateResponse;
import com.qyx.showtick.common.dto.SimplePayCreateRequest;
import com.qyx.showtick.common.entity.PaymentMethod;
import com.qyx.showtick.common.entity.PaymentStatus;
import com.qyx.showtick.common.entity.SimPayment;
import com.qyx.showtick.common.entity.SimPaymentTransaction;
import com.qyx.showtick.common.mapper.SimPaymentMapper;
import com.qyx.showtick.common.mapper.SimPaymentTransactionMapper;
import com.qyx.showtick.simplepay.dto.ShowTickNotificationRequest;
import com.qyx.showtick.simplepay.service.SimPaymentService;
import com.qyx.showtick.simplepay.service.SimPaymentTransService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by Yuxin Qin on 7/28/24
 */
@Service
public class SimPaymentServiceImpl extends ServiceImpl<SimPaymentMapper, SimPayment> implements SimPaymentService {
    @Autowired
    private SimPaymentMapper simPaymentMapper;

    @Autowired
    private SimPaymentTransactionMapper paymentTransactionMapper;

    @Autowired
    private SimPaymentTransService transService;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public SimPayCreateResponse createPayment(SimplePayCreateRequest request) {
        List<SimPayment> payments = simPaymentMapper.selectList(new QueryWrapper<SimPayment>().eq("order_id", request.getOrderId()));
        if(!payments.isEmpty()){
            SimPayment payment = payments.get(0);
            return getSimPayCreateResponse(payment);
        }
        SimPayment payment = new SimPayment();
        payment.setOrderId(request.getOrderId());
        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setStatus(PaymentStatus.PENDING);
        simPaymentMapper.insert(payment);
        return getSimPayCreateResponse(payment);
    }

    private SimPayCreateResponse getSimPayCreateResponse(SimPayment simPayment) {
        SimPayCreateResponse response = new SimPayCreateResponse();
        response.setSimPaymentId(simPayment.getId());
        response.setStatus(simPayment.getStatus());
        response.setPaymentMethod(simPayment.getPaymentMethod());
        response.setPayLink("http://localhost:3000/simpay/payment/" + simPayment.getId());
        response.setAmount(simPayment.getAmount());
        response.setOrderId(simPayment.getOrderId());
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
    public SimPayment getPaymentById(Long paymentId) {
        return simPaymentMapper.selectById(paymentId);
    }



    @Override
    public SimPaymentTransaction processPayment(Long simPaymentId, PaymentMethod paymentMethod, PaymentStatus status) {
        SimPaymentTransaction transaction = transService.createTransaction(simPaymentId, paymentMethod, status);
        SimPayment payment = simPaymentMapper.selectById(simPaymentId);
        // notify show-tick
        notifyShowTickSystem(payment, transaction);
        // return
        return transaction;
    }



    private void notifyShowTickSystem(SimPayment simPayment, SimPaymentTransaction transaction) {
        ShowTickNotificationRequest notificationRequest = new ShowTickNotificationRequest();
        notificationRequest.setSimPaymentId(simPayment.getId());
        notificationRequest.setTransactionId(transaction.getTransactionId());
        notificationRequest.setStatus(transaction.getTransactionStatus());
        notificationRequest.setOrderId(simPayment.getOrderId());

        String showTickUrl = "http://localhost:8080/payments/notify"; //todo
        System.err.println(notificationRequest);
        restTemplate.postForObject(showTickUrl, notificationRequest, Void.class);
    }
}
