package com.qyx.showtick.controller;

import com.qyx.showtick.common.api.CommonResult;
import com.qyx.showtick.common.entity.Payment;
import com.qyx.showtick.dto.CreatePaymentRequest;
import com.qyx.showtick.service.PaymentService;
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

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public CommonResult createPayment(@RequestBody CreatePaymentRequest request) {
        Payment payment = paymentService.createPayment(request);
        return CommonResult.success(payment);
    }
}
