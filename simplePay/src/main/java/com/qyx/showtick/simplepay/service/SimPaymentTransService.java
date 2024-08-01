package com.qyx.showtick.simplepay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qyx.showtick.common.entity.PaymentMethod;
import com.qyx.showtick.common.entity.PaymentStatus;
import com.qyx.showtick.common.entity.SimPayment;
import com.qyx.showtick.common.entity.SimPaymentTransaction;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Yuxin Qin on 7/29/24
 */
public interface SimPaymentTransService extends IService<SimPaymentTransaction> {
    @Transactional
    SimPaymentTransaction createTransaction(Long simPaymentId, PaymentMethod paymentMethod, PaymentStatus status);
}
