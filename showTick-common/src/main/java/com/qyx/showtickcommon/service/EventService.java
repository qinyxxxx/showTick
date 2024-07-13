package com.qyx.showtickcommon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qyx.showtickcommon.dto.EventParam;
import com.qyx.showtickcommon.entity.Event;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by Yuxin Qin on 7/12/24
 */
public interface EventService extends IService<Event> {
    @Transactional
    public int createEvent(EventParam eventParam);

    public Event getEventById(Long id);
}
