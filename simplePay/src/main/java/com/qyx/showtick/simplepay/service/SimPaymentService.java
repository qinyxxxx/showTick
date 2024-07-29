package com.qyx.showtick.simplepay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qyx.showtick.common.dto.SimPayResponse;
import com.qyx.showtick.common.dto.SimplePayRequest;
import com.qyx.showtick.common.entity.PaymentStatus;
import com.qyx.showtick.common.entity.SimPayment;
import com.qyx.showtick.common.entity.SimPaymentTransaction;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Yuxin Qin on 7/28/24
 */
public interface SimPaymentService extends IService<SimPayment> {
    @Transactional
    SimPayResponse createPayment(SimplePayRequest request);
    @Transactional
    void updatePaymentStatus(Long paymentId, PaymentStatus status);
    @Transactional
    void recordTransaction(SimPaymentTransaction transaction);
    SimPayResponse getPaymentById(Long paymentId);
}
