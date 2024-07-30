package com.qyx.showtick.controller;

import com.qyx.showtick.common.api.CommonResult;
import com.qyx.showtick.common.dto.SimPayCreateResponse;
import com.qyx.showtick.common.dto.SimplePayCreateRequest;
import com.qyx.showtick.common.entity.Order;
import com.qyx.showtick.common.entity.Payment;
import com.qyx.showtick.common.entity.User;
import com.qyx.showtick.component.JwtUtil;
import com.qyx.showtick.dto.CreateOrderRequest;
import com.qyx.showtick.dto.OrderDetailResponse;
import com.qyx.showtick.service.OrderService;
import com.qyx.showtick.service.PaymentService;
import com.qyx.showtick.service.UserService;
import com.qyx.showtick.simplepay.service.SimPaymentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Yuxin Qin on 7/25/24
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserService userService;

    @Autowired
    private SimPaymentService simplePayService;

    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Order> createOrder(@RequestBody CreateOrderRequest request, HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader(tokenHeader).substring(7);
        String username = jwtUtil.getUsernameFromToken(token);
        Long userId = userService.geUserByUsername(username).getId();
        Order order = orderService.createOrder(request, userId);
        return CommonResult.success(order);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<OrderDetailResponse> getOrderDetailById(@PathVariable Long id) {
        OrderDetailResponse response = orderService.getOrderDetailsByOrderId(id);
        return CommonResult.success(response);
    }

    @RequestMapping(value = "/{id}/cancel", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult cancelOrder(@PathVariable Long id) {
        int count = orderService.cancelOrder(id);
        return CommonResult.success("cancel success");
    }

    @RequestMapping(value = "/{id}/pay", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<SimPayCreateResponse> payOrder(@PathVariable Long id) {
        // save payment
        Payment payment = paymentService.createPayment(id);

        // 调用 SimplePay API
        SimplePayCreateRequest simplePayRequest = new SimplePayCreateRequest();
        simplePayRequest.setAmount(payment.getAmount());
        simplePayRequest.setOrderId(payment.getOrderId());
        simplePayRequest.setPaymentMethod(payment.getPaymentMethod());

        SimPayCreateResponse simplePayResponse = simplePayService.createPayment(simplePayRequest);

        // 返回simple pay给的支付链接给前端
        return CommonResult.success(simplePayResponse);
    }

}
