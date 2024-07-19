package com.qyx.showtick.mapper;

import com.qyx.showtick.common.entity.Event;
import com.qyx.showtick.dto.EventDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by Yuxin Qin on 7/19/24
 */
@Mapper
public interface EventDTOMapper {
    EventDTOMapper INSTANCE = Mappers.getMapper(EventDTOMapper.class);

    EventDTO toDTO(Event event);
    Event toEntity(EventDTO eventDTO);

}
