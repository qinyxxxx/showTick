package com.qyx.showtick.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qyx.showtick.common.entity.EventCategory;
import com.qyx.showtick.common.mapper.EventCategoryMapper;
import com.qyx.showtick.dto.EventCategoryAdminDTO;
import com.qyx.showtick.mapper.EventCategoryAdminDTOMapper;
import com.qyx.showtick.service.EventCategoryAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Yuxin Qin on 7/23/24
 */
@Service
public class EventCategoryAdminServiceImpl extends ServiceImpl<EventCategoryMapper, EventCategory>  implements EventCategoryAdminService {
    @Autowired
    private EventCategoryMapper eventCategoryMapper;

    @Override
    public int createEventCategory(EventCategoryAdminDTO eventCategoryAdminDTO) {
        EventCategory eventCategory = EventCategoryAdminDTOMapper.INSTANCE.toEntity(eventCategoryAdminDTO);
        return eventCategoryMapper.insert(eventCategory);
    }

    @Override
    public EventCategory getEventCategoryById(Long id) {
        return eventCategoryMapper.selectById(id);
    }

    @Override
    public int updateEventCategory(Long id, EventCategoryAdminDTO eventCategoryAdminDTO) {
        EventCategory eventCategory = EventCategoryAdminDTOMapper.INSTANCE.toEntity(eventCategoryAdminDTO);
        eventCategory.setId(id);
        return eventCategoryMapper.updateById(eventCategory);
    }

    @Override
    public List<EventCategory> getAllEventCategory() {
        return this.list();
    }
}
