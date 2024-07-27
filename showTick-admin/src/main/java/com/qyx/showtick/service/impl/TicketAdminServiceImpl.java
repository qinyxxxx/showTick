package com.qyx.showtick.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qyx.showtick.common.entity.Ticket;
import com.qyx.showtick.common.entity.TicketStatus;
import com.qyx.showtick.common.mapper.TicketMapper;
import com.qyx.showtick.dto.TicketAdminDTO;
import com.qyx.showtick.mapper.TicketAdminDTOMapper;
import com.qyx.showtick.service.TicketAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Yuxin Qin on 7/23/24
 */
@Service
public class TicketAdminServiceImpl extends ServiceImpl<TicketMapper, Ticket> implements TicketAdminService {
    @Autowired
    private TicketMapper ticketMapper;


    @Override
    public int createTicket(TicketAdminDTO ticketAdminDTO) {
        // todo check event exists
        Ticket ticket = TicketAdminDTOMapper.INSTANCE.toEntity(ticketAdminDTO);
        ticket.setStatus(TicketStatus.AVAILABLE);
        return ticketMapper.insert(ticket);
    }

    @Override
    public Ticket getTicketById(Long id) {
        return ticketMapper.selectById(id);
    }

    @Override
    public int updateTicket(Long id, TicketAdminDTO ticketAdminDTO) {
        Ticket ticket = TicketAdminDTOMapper.INSTANCE.toEntity(ticketAdminDTO);
        ticket.setId(id);
        return ticketMapper.updateById(ticket);
    }

    @Override
    public List<Ticket> getAllTickets() {
        return this.list();
    }

    @Override
    public List<Ticket> getTicketsByField(String field, Object value) {
        return ticketMapper.selectList(new QueryWrapper<Ticket>().eq(field, value));
    }
}
