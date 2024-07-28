package com.qyx.showtick.simplepay.dto;

import lombok.Data;

/**
 * Created by Yuxin Qin on 7/28/24
 */
@Data
public class CreatePaymentRequest {
    private Long orderId;
    private float amount;
    private String paymentMethod;
}
