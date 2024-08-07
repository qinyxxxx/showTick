package com.qyx.showtick.simplepay.kafka;

import com.alibaba.fastjson.JSON;
import com.qyx.showtick.common.entity.SimPayment;
import com.qyx.showtick.common.entity.SimPaymentTransaction;
import com.qyx.showtick.simplepay.dto.ShowTickNotificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * Created by Yuxin Qin on 8/7/24
 */
@Service
public class PaymentResultProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentResultProducer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String TOPIC = "payment-result";

    @Autowired
    public PaymentResultProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPaymentResultNotification(SimPayment simPayment, SimPaymentTransaction transaction) {
        ShowTickNotificationRequest notificationRequest = new ShowTickNotificationRequest();
        notificationRequest.setSimPaymentId(simPayment.getId());
        notificationRequest.setTransactionId(transaction.getTransactionId());
        notificationRequest.setStatus(transaction.getTransactionStatus());
        notificationRequest.setOrderId(simPayment.getOrderId());
        String message = JSON.toJSONString(notificationRequest);
        kafkaTemplate.send(TOPIC, message);
        LOGGER.info("Send message with payment status: {}", transaction.getTransactionStatus());
    }
}
