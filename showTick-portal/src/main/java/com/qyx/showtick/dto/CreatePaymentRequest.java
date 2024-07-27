package com.qyx.showtick.dto;

import lombok.Data;

/**
 * Created by Yuxin Qin on 7/26/24
 */
@Data
public class CreatePaymentRequest {
    private Long orderId;
    private String paymentMethod;
    private float amount;
}
