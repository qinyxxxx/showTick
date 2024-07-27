package com.qyx.showtick.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.qyx.showtick.common.entity.Order;
import com.qyx.showtick.dto.CreateOrderRequest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Yuxin Qin on 7/25/24
 */

public interface OrderService extends IService<Order> {
    @Transactional
    Order createOrder(CreateOrderRequest request);


}
