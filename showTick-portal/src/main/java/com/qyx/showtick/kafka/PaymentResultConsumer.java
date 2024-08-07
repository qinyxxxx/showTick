package com.qyx.showtick.kafka;

import com.alibaba.fastjson.JSON;
import com.qyx.showtick.common.exception.Asserts;
import com.qyx.showtick.service.PaymentService;
import com.qyx.showtick.simplepay.dto.ShowTickNotificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Created by Yuxin Qin on 8/7/24
 */
@Service
public class PaymentResultConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentResultConsumer.class);

    @Autowired
    private PaymentService paymentService;

    @KafkaListener(topics = "payment-result", groupId = "payment-group")
    public void listen(String message) {
        try {
            ShowTickNotificationRequest notification = JSON.parseObject(message, ShowTickNotificationRequest.class);
            paymentService.handlePaymentCallBack(notification);
            LOGGER.info("finish get payment result");
        } catch (Exception e) {
            Asserts.fail("failed to get message");
        }
    }
}
