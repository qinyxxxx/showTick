package com.qyx.showtick.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Yuxin Qin on 7/23/24
 */
@Data
@EqualsAndHashCode
public class TicketAdminDTO {
    private Long eventId;
    private Long seatCategoryId;
    private float price;
    private int totalQuantity;
    private int remainingQuantity;
}
