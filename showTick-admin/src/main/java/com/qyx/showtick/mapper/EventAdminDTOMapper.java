package com.qyx.showtick.mapper;

import com.qyx.showtick.dto.EventAdminDTO;
import com.qyx.showtick.common.entity.Event;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * Created by Yuxin Qin on 7/19/24
 */
@Mapper
public interface EventAdminDTOMapper {
    EventAdminDTOMapper INSTANCE = Mappers.getMapper(EventAdminDTOMapper.class);

    EventAdminDTO toDTO(Event event);

    Event toEntity(EventAdminDTO eventAdminDTO);

    void updateEventFromDto(EventAdminDTO eventAdminDTO, @MappingTarget Event event);

}
