package com.qyx.showtick.simplepay.controller;

import com.qyx.showtick.common.api.CommonResult;
import com.qyx.showtick.common.dto.SimplePayCreateRequest;

import com.qyx.showtick.common.dto.SimPayCreateResponse;
import com.qyx.showtick.common.entity.*;
import com.qyx.showtick.simplepay.dto.SimGetPayResponse;
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

    @RequestMapping(value = "/{simPaymentId}/pay", method = RequestMethod.POST)
    public CommonResult<SimGetPayResponse> pay(@PathVariable Long simPaymentId,
                                               @RequestParam PaymentMethod paymentMethod,
                                               @RequestParam PaymentStatus paymentStatus) {
        SimPaymentTransaction transaction = paymentService.processPayment(simPaymentId, paymentMethod, paymentStatus);
        SimPayment payment = paymentService.getPaymentById(transaction.getPaymentId());
        SimGetPayResponse response = new SimGetPayResponse();
        response.setSimPayment(payment);
        response.setTransaction(transaction);
        return CommonResult.success(response);
    }

    @RequestMapping(value = "/{paymentId}", method = RequestMethod.GET)
    public CommonResult<SimPayment> getPayment(@PathVariable Long paymentId) {
        SimPayment payment = paymentService.getPaymentById(paymentId);
        return CommonResult.success(payment);
    }
}
