package com.qyx.showtick.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qyx.showtick.common.dto.EventParam;
import com.qyx.showtick.common.entity.Event;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Yuxin Qin on 7/12/24
 */
public interface EventService extends IService<Event> {


    Event getEventById(Long id);


}
