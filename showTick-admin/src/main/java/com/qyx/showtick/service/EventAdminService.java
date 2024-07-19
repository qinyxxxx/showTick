package com.qyx.showtick.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qyx.showtick.common.entity.Event;
import com.qyx.showtick.dto.EventAdminDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Yuxin Qin on 7/18/24
 */
public interface EventAdminService extends IService<Event> {
    @Transactional
    int createEvent(EventAdminDTO eventAdminDTO);



    Event getEventById(Long id);

    @Transactional
    int updateEvent(Long id, EventAdminDTO eventAdminDTO);

    List<Event> getAllEvents();
}
