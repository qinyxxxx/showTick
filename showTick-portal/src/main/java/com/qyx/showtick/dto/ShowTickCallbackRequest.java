package com.qyx.showtick.dto;

import lombok.Data;

/**
 * Created by Yuxin Qin on 7/28/24
 */
@Data
public class ShowTickCallbackRequest {
    private Long paymentId;
    private String status;
}
