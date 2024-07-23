package com.qyx.showtick.mapper;

import com.qyx.showtick.common.entity.SeatCategory;
import com.qyx.showtick.dto.SeatCategoryAdminDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by Yuxin Qin on 7/19/24
 */
@Mapper
public interface SeatCategoryAdminDTOMapper {
    SeatCategoryAdminDTOMapper INSTANCE = Mappers.getMapper(SeatCategoryAdminDTOMapper.class);

    SeatCategoryAdminDTO toDTO(SeatCategory seatCategory);

    SeatCategory toEntity(SeatCategoryAdminDTO seatCategoryAdminDTO);

}
