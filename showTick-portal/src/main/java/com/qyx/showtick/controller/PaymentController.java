package com.qyx.showtick.controller;

import com.qyx.showtick.common.api.CommonResult;
import com.qyx.showtick.service.PaymentService;
import com.qyx.showtick.simplepay.dto.ShowTickNotificationRequest;
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

    @RequestMapping(value = "/notify", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult notify(@RequestBody ShowTickNotificationRequest request) {
        paymentService.handlePaymentCallBack(request);
        return CommonResult.success("Callback received");
    }
}
