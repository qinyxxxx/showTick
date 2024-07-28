package com.qyx.showtick.controller;

import com.qyx.showtick.common.api.CommonResult;
import com.qyx.showtick.common.entity.Order;
import com.qyx.showtick.common.entity.User;
import com.qyx.showtick.component.JwtUtil;
import com.qyx.showtick.dto.CreateOrderRequest;
import com.qyx.showtick.service.OrderService;
import com.qyx.showtick.service.UserService;
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
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public CommonResult createOrder(@RequestBody CreateOrderRequest request, HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader(tokenHeader).substring(7);
        String username = jwtUtil.getUsernameFromToken(token);
        Long userId = userService.geUserByUsername(username).getId();
        Order order = orderService.createOrder(request, userId);
        return CommonResult.success(order);
    }
}
