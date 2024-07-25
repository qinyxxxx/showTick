package com.qyx.showtick.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qyx.showtick.common.entity.Ticket;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Yuxin Qin on 7/23/24
 */
public interface TicketMapper extends BaseMapper<Ticket> {
//    @Select("SELECT * FROM ticket WHERE event_id = #{eventId} AND status = 0")
//    List<Ticket> getAvailableSeatsByEventId(Long eventId);
}
