package com.qyx.showtick.simplepay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qyx.showtick.simplepay.dto.CreatePaymentRequest;
import com.qyx.showtick.simplepay.dto.PaymentResponse;
import com.qyx.showtick.simplepay.entity.Payment;
import com.qyx.showtick.simplepay.entity.PaymentTransaction;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Yuxin Qin on 7/28/24
 */
public interface PaymentService extends IService<Payment> {
    @Transactional
    Payment createPayment(CreatePaymentRequest request);
    @Transactional
    void updatePaymentStatus(Long paymentId, String status);
    @Transactional
    void recordTransaction(PaymentTransaction transaction);
    PaymentResponse getPaymentById(Long paymentId);
}
