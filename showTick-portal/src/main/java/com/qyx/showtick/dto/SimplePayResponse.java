package com.qyx.showtick.dto;

import lombok.Data;

/**
 * Created by Yuxin Qin on 7/28/24
 */
@Data
public class SimplePayResponse {
    private Long paymentId;
    private String paymentLink;
}
