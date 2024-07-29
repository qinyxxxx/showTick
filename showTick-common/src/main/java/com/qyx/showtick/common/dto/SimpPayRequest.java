package com.qyx.showtick.common.dto;

import com.qyx.showtick.common.entity.PaymentMethod;
import lombok.Data;

/**
 * Created by Yuxin Qin on 7/29/24
 */
@Data
public class SimpPayRequest {
    private Long paymentId;
    private Long orderId;
}
