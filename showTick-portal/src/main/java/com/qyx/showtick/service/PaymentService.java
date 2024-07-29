package com.qyx.showtick.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qyx.showtick.common.entity.Payment;
import com.qyx.showtick.dto.CreatePaymentRequest;
import com.qyx.showtick.simplepay.dto.ShowTickNotificationRequest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Yuxin Qin on 7/26/24
 */
public interface PaymentService extends IService<Payment> {
    @Transactional
    Payment createPayment(CreatePaymentRequest request);

    @Transactional
    void handlePaymentCallBack(ShowTickNotificationRequest request);
}
