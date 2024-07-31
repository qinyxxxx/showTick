package com.qyx.showtick.simplepay.dto;

import com.qyx.showtick.common.entity.SimPayment;
import com.qyx.showtick.common.entity.SimPaymentTransaction;
import lombok.Data;

/**
 * Created by Yuxin Qin on 7/30/24
 */
@Data
public class SimGetPayResponse {
    private SimPaymentTransaction transaction;
    private SimPayment simPayment;
}
