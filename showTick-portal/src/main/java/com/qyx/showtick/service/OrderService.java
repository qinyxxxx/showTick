package com.qyx.showtick.service;


import com.qyx.showtick.common.entity.Order;
import com.qyx.showtick.dto.CreateOrderRequest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Yuxin Qin on 7/25/24
 */

public interface OrderService {
    @Transactional
    Order createOrder(CreateOrderRequest request);


}
