package com.qyx.showtick.service;

import com.qyx.showtick.common.entity.Ticket;

import java.util.List;

/**
 * Created by Yuxin Qin on 7/25/24
 */
public interface TicketService {

    List<Ticket> getAvailableSeatsByEventId(Long eventId);
}
