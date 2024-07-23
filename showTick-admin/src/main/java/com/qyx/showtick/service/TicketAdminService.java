package com.qyx.showtick.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qyx.showtick.common.entity.Ticket;
import com.qyx.showtick.dto.TicketAdminDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Yuxin Qin on 7/23/24
 */
public interface TicketAdminService extends IService<Ticket> {
    @Transactional
    int createTicket(TicketAdminDTO ticketAdminDTO);

    Ticket getTicketById(Long id);

    @Transactional
    int updateTicket(Long id, TicketAdminDTO ticketAdminDTO);

    List<Ticket> getAllTickets();
}
