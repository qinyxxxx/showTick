package com.qyx.showtick.mapper;

import com.qyx.showtick.common.entity.EventCategory;
import com.qyx.showtick.dto.EventCategoryAdminDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by Yuxin Qin on 7/23/24
 */
@Mapper
public interface EventCategoryAdminDTOMapper {
    EventCategoryAdminDTOMapper INSTANCE = Mappers.getMapper(EventCategoryAdminDTOMapper.class);
    EventCategoryAdminDTO toDTO(EventCategory eventCategory);
    EventCategory toEntity(EventCategoryAdminDTO eventCategoryAdminDTO);
}
