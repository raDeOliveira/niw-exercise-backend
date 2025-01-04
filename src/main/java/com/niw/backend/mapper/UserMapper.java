package com.niw.backend.mapper;

import com.niw.backend.dto.UserDTO;
import com.niw.backend.entity.UserEntity;
import com.niw.backend.payload.request.UserRequest;
import com.niw.backend.payload.response.UserResponse;
import org.mapstruct.*;

/**
@project: niw-java-exercise
@author: raOliveira
@time: 7:52 PM
**/

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserEntity toEntity(UserDTO userDto);
    UserDTO toDto(UserEntity user);
    UserResponse toResponse(UserDTO userDto);
    UserDTO fromRequest(UserRequest userRequest);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserEntity partialUpdate(UserDTO userDto, @MappingTarget UserEntity user);
}
