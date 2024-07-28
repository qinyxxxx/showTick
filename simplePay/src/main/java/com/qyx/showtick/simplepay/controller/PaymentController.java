package com.qyx.showtick.simplepay.controller;

import com.qyx.showtick.simplepay.dto.CreatePaymentRequest;
import com.qyx.showtick.simplepay.dto.PaymentResponse;
import com.qyx.showtick.simplepay.entity.Payment;
import com.qyx.showtick.simplepay.service.PaymentService;
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
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody CreatePaymentRequest request) {
        Payment payment = paymentService.createPayment(request);
        PaymentResponse response = new PaymentResponse();
        response.setPaymentMethod(payment.getPaymentMethod());
        response.setStatus(payment.getPaymentStatus());
        response.setAmount(payment.getAmount());
        response.setOrderId(payment.getOrderId());
        return ResponseEntity.ok(response);
    }
}
