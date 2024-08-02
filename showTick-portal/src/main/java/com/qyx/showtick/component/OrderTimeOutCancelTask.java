package com.qyx.showtick.component;

import com.qyx.showtick.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Yuxin Qin on 8/1/24
 */
@Component
public class OrderTimeOutCancelTask {
    private final Logger LOGGER = LoggerFactory.getLogger(OrderTimeOutCancelTask.class);

    @Autowired
    private OrderService orderService;

    /**
     * cron表达式：Seconds Minutes Hours DayOfMonth Month DayOfWeek [Year]
     * 每10分钟扫描一次，扫描超时未支付订单，进行取消操作
     */
    @Scheduled(cron = "0 0/10 * ? * ?") //todo change the cron fluency
    private void cancelTimeOutOrder(){
        int count = orderService.cancelTimeoutOrders();
        LOGGER.info("cancel order num：{}",count);
    }
}
