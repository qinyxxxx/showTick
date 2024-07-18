package com.qyx.showtick.mapper;

import com.qyx.showtick.common.entity.User;
import com.qyx.showtick.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * Created by Yuxin Qin on 7/18/24
 */
@Mapper
public interface UserDTOMapper {
    UserDTOMapper INSTANCE = Mappers.getMapper(UserDTOMapper.class);

    UserDTO toDTO(User user);
    User toEntity(UserDTO userDTO);

    void updateUserFromDto(UserDTO userDTO, @MappingTarget User user);
}
