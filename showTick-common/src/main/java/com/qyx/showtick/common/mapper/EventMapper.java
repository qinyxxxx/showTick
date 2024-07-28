package com.qyx.showtick.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qyx.showtick.common.entity.Event;
import org.apache.ibatis.annotations.Update;

/**
 * Created by Yuxin Qin on 7/12/24
 */
public interface EventMapper extends BaseMapper<Event> {
    @Update("UPDATE event SET remaining_ticket = #{remainingTicket}, version = version+1 WHERE id = #{id} AND version = #{version}")
    int updateWithOptimisticLock(Event event);
}
