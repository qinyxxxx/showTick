package com.qyx.showtick.mapper;

import com.qyx.showtick.common.entity.SeatCategory;
import com.qyx.showtick.dto.SeatCategoryAdminDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by Yuxin Qin on 7/19/24
 */
@Mapper
public interface SeatCategoryAdminMapper {
    SeatCategoryAdminMapper INSTANCE = Mappers.getMapper(SeatCategoryAdminMapper.class);

    SeatCategoryAdminDTO toDTO(SeatCategory seatCategory);

    SeatCategory toEntity(SeatCategoryAdminDTO seatCategoryAdminDTO);

}
