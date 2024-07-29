package com.qyx.showtick.common.dto;

import com.qyx.showtick.common.entity.PaymentMethod;
import lombok.Data;

/**
 * Created by Yuxin Qin on 7/28/24
 */
@Data
public class SimplePayRequest {
    private Long orderId;
    private float amount;
    private PaymentMethod paymentMethod;
}
