package com.example.Server.dao.mapper;

import com.example.Server.api.dto.request.RegisterRequest;
import com.example.Server.api.dto.response.UserResponse;
import com.example.Server.dao.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toDto(UserEntity userEntity);

    @Mapping(target = "id", ignore = true)
    UserEntity toEntity(RegisterRequest request);
}