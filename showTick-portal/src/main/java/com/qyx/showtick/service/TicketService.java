package com.qyx.showtick.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qyx.showtick.common.entity.Ticket;

import java.util.List;

/**
 * Created by Yuxin Qin on 7/25/24
 */
public interface TicketService extends IService<Ticket> {

    List<Ticket> getAvailableSeatsByEventId(Long eventId);
}
