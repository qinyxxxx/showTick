package com.qyx.showtick.simplepay.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qyx.showtick.common.entity.PaymentStatus;
import com.qyx.showtick.common.entity.SimPayment;
import com.qyx.showtick.common.entity.SimPaymentTransaction;
import com.qyx.showtick.common.mapper.SimPaymentMapper;
import com.qyx.showtick.common.mapper.SimPaymentTransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


/**
 * Created by Yuxin Qin on 7/29/24
 */
@Service
public class SimPaymentTransServiceImpl extends ServiceImpl<SimPaymentTransactionMapper, SimPaymentTransaction>  implements SimPaymentTransService {
    @Autowired
    private SimPaymentMapper simPaymentMapper;

    @Autowired
    private SimPaymentTransactionMapper paymentTransactionMapper;

    @Override
    public SimPaymentTransaction createTransaction(Long paymentId, PaymentStatus status) {
        // save transaction
        SimPaymentTransaction transaction = new SimPaymentTransaction();
        transaction.setTransactionId(UUID.randomUUID().toString());
        transaction.setTransactionStatus(status);
        transaction.setPaymentId(paymentId);
        paymentTransactionMapper.insert(transaction);

        // update payment status
        SimPayment payment = simPaymentMapper.selectById(paymentId);
        payment.setStatus(status);
        simPaymentMapper.updateById(payment);
        return transaction;
    }

}
