package com.qyx.showtick.simplepay.controller;

import com.qyx.showtick.common.api.CommonResult;
import com.qyx.showtick.common.dto.SimplePayCreateRequest;

import com.qyx.showtick.common.dto.SimPayCreateResponse;
import com.qyx.showtick.common.entity.PaymentStatus;
import com.qyx.showtick.common.entity.SimPaymentTransaction;
import com.qyx.showtick.simplepay.service.SimPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Yuxin Qin on 7/28/24
 */
@RestController
@RequestMapping("/simple-pay/payments")
public class SimPayController {

    @Autowired
    private SimPaymentService paymentService;


    @PostMapping
    public CommonResult<SimPayCreateResponse> createPayment(@RequestBody SimplePayCreateRequest request) {
        SimPayCreateResponse response = paymentService.createPayment(request);
//        SimPayResponse response = new SimPayResponse();
//        response.setPaymentMethod(payment.getPaymentMethod());
//        response.setStatus(payment.getPaymentStatus());
//        response.setAmount(payment.getAmount());
//        response.setOrderId(payment.getOrderId());
        return CommonResult.success(response);
    }

    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public CommonResult<SimPaymentTransaction> pay(@RequestParam Long paymentId, @RequestParam PaymentStatus paymentStatus) {
        SimPaymentTransaction transaction = paymentService.processPayment(paymentId, paymentStatus);


        return CommonResult.success(transaction);
    }
}
