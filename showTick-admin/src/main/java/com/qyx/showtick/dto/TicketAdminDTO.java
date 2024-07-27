package com.qyx.showtick.dto;

import com.qyx.showtick.common.entity.TicketStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Yuxin Qin on 7/23/24
 */
@Data
@EqualsAndHashCode
public class TicketAdminDTO {
    private Long eventId;
    private String section;
    private String seatRow;
    private String seatNumber;
    private float price;
    private TicketStatus status;
}
