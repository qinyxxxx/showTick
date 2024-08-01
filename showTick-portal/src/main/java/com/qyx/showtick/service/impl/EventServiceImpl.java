package com.qyx.showtick.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qyx.showtick.common.entity.Event;
import com.qyx.showtick.common.mapper.EventMapper;
import com.qyx.showtick.dto.EventDTO;
import com.qyx.showtick.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Yuxin Qin on 7/12/24
 */
@Service
public class EventServiceImpl extends ServiceImpl<EventMapper, Event> implements EventService {
    @Autowired
    private EventMapper eventMapper;

    @Override
    public List<Event> getTop5Events() {
        QueryWrapper<Event> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("remaining_ticket").last("LIMIT 5");
        queryWrapper.ge("remaining_ticket", 0);
        queryWrapper.eq("status", 0);
        return eventMapper.selectList(queryWrapper);
    }

}
