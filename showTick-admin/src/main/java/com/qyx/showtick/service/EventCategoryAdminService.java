package com.qyx.showtick.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qyx.showtick.common.entity.EventCategory;
import com.qyx.showtick.dto.EventCategoryAdminDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Yuxin Qin on 7/23/24
 */
public interface EventCategoryAdminService extends IService<EventCategory> {
    @Transactional
    int createEventCategory(EventCategoryAdminDTO eventCategoryAdminDTO);

    EventCategory getEventCategoryById(Long id);

    @Transactional
    int updateEventCategory(Long id, EventCategoryAdminDTO eventCategoryAdminDTO);

    List<EventCategory> getAllEventCategory();
}
