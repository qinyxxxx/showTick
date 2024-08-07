package com.qyx.showtick.simplepay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qyx.showtick.common.entity.PaymentMethod;
import com.qyx.showtick.common.entity.PaymentStatus;
import com.qyx.showtick.common.entity.SimPayment;
import com.qyx.showtick.common.entity.SimPaymentTransaction;
import com.qyx.showtick.common.mapper.SimPaymentMapper;
import com.qyx.showtick.common.mapper.SimPaymentTransactionMapper;
import com.qyx.showtick.simplepay.service.SimPaymentTransService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public SimPaymentTransaction createTransaction(Long simPaymentId, PaymentMethod paymentMethod, PaymentStatus status) {
        SimPaymentTransaction transaction;
        List<SimPaymentTransaction> transactions = paymentTransactionMapper.selectList(new QueryWrapper<SimPaymentTransaction>().eq("payment_id", simPaymentId));
        if(!transactions.isEmpty()){
            transaction = transactions.get(0);
        } else {
            // save transaction
            transaction = new SimPaymentTransaction();
            transaction.setTransactionId(UUID.randomUUID().toString());
            transaction.setTransactionStatus(status);
            transaction.setPaymentId(simPaymentId);
            transaction.setUserId(simPaymentMapper.selectById(simPaymentId).getUserId());
            paymentTransactionMapper.insert(transaction);
        }
        // update payment status
        SimPayment payment = simPaymentMapper.selectById(simPaymentId);
        payment.setStatus(status);
        payment.setPaymentMethod(paymentMethod);
        simPaymentMapper.updateById(payment);
        return transaction;
    }

}
