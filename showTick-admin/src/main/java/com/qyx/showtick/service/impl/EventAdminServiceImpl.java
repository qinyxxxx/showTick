package com.qyx.showtick.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qyx.showtick.common.entity.Event;
import com.qyx.showtick.common.mapper.EventMapper;
import com.qyx.showtick.dto.EventAdminDTO;
import com.qyx.showtick.mapper.EventAdminDTOMapper;
import com.qyx.showtick.service.EventAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Yuxin Qin on 7/18/24
 */
@Service
public class EventAdminServiceImpl extends ServiceImpl<EventMapper, Event> implements EventAdminService {
    @Autowired
    private EventMapper eventMapper;

    @Override
    public int createEvent(EventAdminDTO eventAdminDTO) {
        // todo upload file
        // todo check category exists
        Event event = EventAdminDTOMapper.INSTANCE.toEntity(eventAdminDTO);
        return eventMapper.insert(event);
    }

    @Override
    public Event getEventById(Long id) {
        return eventMapper.selectById(id);
    }

    @Override
    public int updateEvent(Long id, EventAdminDTO eventAdminDTO) {
        // todo upload file
        Event event = EventAdminDTOMapper.INSTANCE.toEntity(eventAdminDTO);
        event.setId(id);
        return eventMapper.updateById(event);
    }

    @Override
    public List<Event> getAllEvents() {
        return this.list();
    }

    @Override
    public List<Event> getEventsByField(String fieldName, Object value) {
        QueryWrapper<Event> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(fieldName, value);
        return eventMapper.selectList(queryWrapper);
    }

}
