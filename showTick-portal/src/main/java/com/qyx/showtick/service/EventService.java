package com.qyx.showtick.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qyx.showtick.common.entity.Event;
import com.qyx.showtick.dto.EventDTO;

import java.util.List;

/**
 * Created by Yuxin Qin on 7/12/24
 */
public interface EventService extends IService<Event> {


    Event getEventById(Long id);

    List<EventDTO> getAllEvents();

}
