package com.qyx.showtick.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qyx.showtick.common.api.CommonResult;
import com.qyx.showtick.common.dto.SimPayCreateResponse;
import com.qyx.showtick.common.dto.SimplePayCreateRequest;
import com.qyx.showtick.common.entity.Order;
import com.qyx.showtick.common.entity.Payment;
import com.qyx.showtick.component.JwtUtil;
import com.qyx.showtick.dto.CreateOrderRequest;
import com.qyx.showtick.dto.OrderDTO;
import com.qyx.showtick.dto.OrderDetailResponse;
import com.qyx.showtick.service.OrderService;
import com.qyx.showtick.service.PaymentService;
import com.qyx.showtick.simplepay.service.SimPaymentService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Yuxin Qin on 7/25/24
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private SimPaymentService simplePayService;

    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping(method = RequestMethod.GET)
    public CommonResult<IPage<OrderDTO>> getUserOrdersByPage(@RequestParam int pageNum,
                                                             @RequestParam int pageSize, HttpServletRequest httpRequest) {
        Long userId = getUserIdFromRequest(httpRequest);
        IPage<OrderDTO> orderIPage = orderService.getOrdersByUsername(userId, pageNum, pageSize);
        LOGGER.info("Get orders by page success, pageNum: {}, pageSize: {}", pageNum, pageSize);
        return CommonResult.success(orderIPage);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Order> createOrder(@RequestBody CreateOrderRequest request, HttpServletRequest httpRequest) {
        Long userId = getUserIdFromRequest(httpRequest);
        Order order = orderService.createOrder(request, userId);
        LOGGER.info("Create order success");
        return CommonResult.success(order);
    }

//    @CheckUserId(resourceIdMethod = "getOrderUserId")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<OrderDetailResponse> getOrderDetailById(@PathVariable Long id, HttpServletRequest httpRequest) {
        if(!checkUserId(id, httpRequest)){
            LOGGER.error("Token and user does not match");
            return CommonResult.forbidden(null);
        }
        OrderDetailResponse response = orderService.getOrderDetailsByOrderId(id);
        LOGGER.info("Order Detail {}", response);
        return CommonResult.success(response);
    }

    @RequestMapping(value = "/{id}/cancel", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult cancelOrder(@PathVariable Long id, HttpServletRequest httpRequest) {
        if(!checkUserId(id, httpRequest)){
            LOGGER.error("Token and user does not match");
            return CommonResult.forbidden(null);
        }
        int count = orderService.cancelOrder(id);
        return CommonResult.success("cancel success");
    }

    @RequestMapping(value = "/{id}/pay", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<SimPayCreateResponse> payOrder(@PathVariable Long id, HttpServletRequest httpRequest) {
        if(!checkUserId(id, httpRequest)){
            LOGGER.error("Token and user does not match");
            return CommonResult.forbidden(null);
        }
        Long userId = getUserIdFromRequest(httpRequest);

        // save payment
        Payment payment = paymentService.createPayment(id);
        LOGGER.info("Save payment");

        // 调用 SimplePay API
        SimplePayCreateRequest simplePayRequest = new SimplePayCreateRequest();
        simplePayRequest.setAmount(payment.getAmount());
        simplePayRequest.setOrderId(payment.getOrderId());
        simplePayRequest.setPaymentMethod(payment.getPaymentMethod());

        SimPayCreateResponse simplePayResponse = simplePayService.createPayment(simplePayRequest, userId);
        LOGGER.info("Call simple payment success");
        // 返回simple pay给的支付链接给前端
        return CommonResult.success(simplePayResponse);
    }

    private boolean checkUserId(Long orderId, HttpServletRequest httpRequest) {
        Long userIdFromToken = getUserIdFromRequest(httpRequest);
        Long userIdFromOrder = orderService.getUserIdByOrderId(orderId);
        return userIdFromOrder.equals(userIdFromToken);
    }

    private Long getUserIdFromRequest(HttpServletRequest httpRequest){
        String token = httpRequest.getHeader(tokenHeader).substring(tokenHead.length());
        return jwtUtil.getUserIdFromToken(token);
    }
}
