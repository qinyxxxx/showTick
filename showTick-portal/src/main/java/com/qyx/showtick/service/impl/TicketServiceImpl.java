package com.qyx.showtick.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qyx.showtick.common.entity.Order;
import com.qyx.showtick.common.entity.Ticket;
import com.qyx.showtick.common.entity.TicketStatus;
import com.qyx.showtick.common.mapper.TicketMapper;
import com.qyx.showtick.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Yuxin Qin on 7/25/24
 */
@Service
public class TicketServiceImpl extends ServiceImpl<TicketMapper, Ticket> implements TicketService {
    @Autowired
    private TicketMapper ticketMapper;

    @Override
    public List<Ticket> getAvailableSeatsByEventId(Long eventId) {
        return ticketMapper.selectList(new QueryWrapper<Ticket>()
                .eq("event_id", eventId)
                .eq("status", TicketStatus.AVAILABLE));
    }

    @Override
    public int updateTicketStatus(Long ticketId, TicketStatus status) {
        Ticket ticket = ticketMapper.selectById(ticketId);
        ticket.setStatus(status);
        int count = ticketMapper.updateById(ticket);
        if(count != 1){
            throw new RuntimeException("update ticket status failed");
        }
        return count;
    }
}
