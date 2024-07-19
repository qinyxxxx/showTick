package com.qyx.showtick.service.impl;

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
    public Event getEventById(Long id) {
        return eventMapper.selectById(id);
    }

    @Override
    public List<EventDTO> getAllEvents() {
        return null;
    }

    void test(){
        Event event = new Event();
        event.setId(100L);
    }


}
