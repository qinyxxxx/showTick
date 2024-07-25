package com.qyx.showtick.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qyx.showtick.common.entity.Ticket;
import com.qyx.showtick.common.entity.TicketStatus;
import com.qyx.showtick.common.mapper.TicketMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Yuxin Qin on 7/25/24
 */
@Service
public class TicketServiceImpl implements TicketService {
    @Autowired
    private TicketMapper ticketMapper;

    @Override
    public List<Ticket> getAvailableSeatsByEventId(Long eventId) {
        return ticketMapper.selectList(new QueryWrapper<Ticket>()
                .eq("event_id", eventId)
                .eq("status", TicketStatus.AVAILABLE));
    }
}
