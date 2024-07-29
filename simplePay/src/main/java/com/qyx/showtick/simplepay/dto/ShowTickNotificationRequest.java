package com.qyx.showtick.simplepay.dto;

import com.qyx.showtick.common.entity.PaymentStatus;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by Yuxin Qin on 7/29/24
 */
@Data
public class ShowTickNotificationRequest {
    private Long paymentId;
    private String transactionId;
    private PaymentStatus status;
}
