package com.qyx.showtick.simplepay.controller;

import com.qyx.showtick.common.dto.SimplePayRequest;

import com.qyx.showtick.common.dto.SimPayResponse;
import com.qyx.showtick.simplepay.service.SimPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Yuxin Qin on 7/28/24
 */
@RestController
@RequestMapping("/pay/payments")
public class SimPayController {

    @Autowired
    private SimPaymentService paymentService;

    @PostMapping
    public ResponseEntity<SimPayResponse> createPayment(@RequestBody SimplePayRequest request) {
        SimPayResponse response = paymentService.createPayment(request);
//        SimPayResponse response = new SimPayResponse();
//        response.setPaymentMethod(payment.getPaymentMethod());
//        response.setStatus(payment.getPaymentStatus());
//        response.setAmount(payment.getAmount());
//        response.setOrderId(payment.getOrderId());
        return ResponseEntity.ok(response);
    }
}
