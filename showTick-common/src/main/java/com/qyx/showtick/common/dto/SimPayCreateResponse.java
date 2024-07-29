package com.qyx.showtick.common.dto;

import com.qyx.showtick.common.entity.PaymentMethod;
import com.qyx.showtick.common.entity.PaymentStatus;
import lombok.Data;

/**
 * Created by Yuxin Qin on 7/28/24
 */
@Data
public class SimPayCreateResponse {
    private Long paymentId;
    private Long orderId;
    private float amount;
    private PaymentMethod paymentMethod;
    private PaymentStatus status;
    private String payLink;
}
