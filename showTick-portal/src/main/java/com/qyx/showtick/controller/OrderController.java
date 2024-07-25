package com.qyx.showtick.controller;

import com.qyx.showtick.common.api.CommonResult;
import com.qyx.showtick.common.entity.Order;
import com.qyx.showtick.dto.CreateOrderRequest;
import com.qyx.showtick.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Yuxin Qin on 7/25/24
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public CommonResult createOrder(@RequestBody CreateOrderRequest request) {
        Order order = orderService.createOrder(request);
        return CommonResult.success(order);
    }
}
