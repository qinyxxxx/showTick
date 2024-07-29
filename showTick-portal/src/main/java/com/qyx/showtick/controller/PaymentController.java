package com.qyx.showtick.controller;

import com.qyx.showtick.common.api.CommonResult;
import com.qyx.showtick.common.dto.SimPayResponse;
import com.qyx.showtick.common.entity.Payment;
import com.qyx.showtick.dto.CreatePaymentRequest;
import com.qyx.showtick.common.dto.SimplePayRequest;
import com.qyx.showtick.service.PaymentService;
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

    @Autowired
    private SimPaymentService simplePayService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<SimPayResponse> createPayment(@RequestBody CreatePaymentRequest request) {
        // 落库一条记录
        Payment payment = paymentService.createPayment(request);

        // 调用 SimplePay API
        SimplePayRequest simplePayRequest = new SimplePayRequest();
        simplePayRequest.setAmount(payment.getAmount());
        simplePayRequest.setOrderId(payment.getOrderId());
        simplePayRequest.setPaymentMethod(payment.getPaymentMethod());
        SimPayResponse simplePayResponse = simplePayService.createPayment(simplePayRequest);

        // 返回simple pay给的支付链接给前端
        return CommonResult.success(simplePayResponse);
    }

//    @RequestMapping(method = RequestMethod.POST)
//    @ResponseBody
//    public CommonResult handlePaymentCallback(@RequestBody SimplePayCallbackRequest request) {
//        // 处理 SimplePay 的回调通知
//        paymentService.updatePaymentStatus(request.getPaymentId(), request.getStatus());
//        return ResponseEntity.ok("Callback received");
//    }
}
