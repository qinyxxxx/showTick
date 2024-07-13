package com.qyx.showtickcommon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.qyx.showtickcommon.dto.EventParam;
import com.qyx.showtickcommon.entity.Event;
import com.qyx.showtickcommon.mapper.EventMapper;
import com.qyx.showtickcommon.service.EventService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Yuxin Qin on 7/12/24
 */
@Service
public class EventServiceImpl extends ServiceImpl<EventMapper, Event> implements EventService {
    @Autowired
    private EventMapper eventMapper;

    @Override
    public int createEvent(EventParam eventParam) {
        Event event = new Event();
        BeanUtils.copyProperties(eventParam, event);
        return eventMapper.insert(event);
    }

    @Override
    public Event getEventById(Long id) {
        return eventMapper.selectById(id);
    }
}
