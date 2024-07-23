package com.qyx.showtick.mapper;

import com.qyx.showtick.common.entity.Ticket;
import com.qyx.showtick.dto.TicketAdminDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by Yuxin Qin on 7/23/24
 */
@Mapper
public interface TicketAdminDTOMapper {
    TicketAdminDTOMapper INSTANCE = Mappers.getMapper(TicketAdminDTOMapper.class);

    TicketAdminDTO toDTO(Ticket ticket);

    Ticket toEntity(TicketAdminDTO ticketAdminDTO);
}
