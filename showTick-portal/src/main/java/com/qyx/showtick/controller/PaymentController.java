package com.qyx.showtick.controller;

import com.qyx.showtick.common.api.CommonResult;
import com.qyx.showtick.common.dto.SimPayCreateResponse;
import com.qyx.showtick.common.entity.Payment;
import com.qyx.showtick.dto.CreatePaymentRequest;
import com.qyx.showtick.common.dto.SimplePayCreateRequest;
import com.qyx.showtick.service.PaymentService;
import com.qyx.showtick.simplepay.dto.ShowTickNotificationRequest;
import com.qyx.showtick.simplepay.service.SimPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Yuxin Qin on 7/26/24
 */
@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    PaymentService paymentService;



//    @RequestMapping(method = RequestMethod.POST)
//    @ResponseBody
//    public CommonResult<SimPayCreateResponse> createPayment(@RequestBody CreatePaymentRequest request) {
//        // save payment
//        Payment payment = paymentService.createPayment(request);
//
//        // 调用 SimplePay API
//        SimplePayCreateRequest simplePayRequest = new SimplePayCreateRequest();
//        simplePayRequest.setAmount(payment.getAmount());
//        simplePayRequest.setOrderId(payment.getOrderId());
//        simplePayRequest.setPaymentMethod(payment.getPaymentMethod());
//        SimPayCreateResponse simplePayResponse = simplePayService.createPayment(simplePayRequest);
//
//        // 返回simple pay给的支付链接给前端
//        return CommonResult.success(simplePayResponse);
//    }

    @RequestMapping(value = "/notify", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult notify(@RequestBody ShowTickNotificationRequest request) {
        // 处理 SimplePay 的回调通知
        System.err.println("here i am");
        paymentService.handlePaymentCallBack(request);
        return CommonResult.success("Callback received");
    }
}
