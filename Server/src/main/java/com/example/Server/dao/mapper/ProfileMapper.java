package com.example.Server.dao.mapper;

import com.example.Server.api.dto.request.ProfileRequest;
import com.example.Server.api.dto.response.ProfileResponse;
import com.example.Server.dao.entity.ProfileEntity;
import com.example.Server.domain.model.ProfileModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    ProfileModel toDomain(ProfileEntity entity);
    ProfileEntity toEntity(ProfileModel domain);
    ProfileModel toDomain(ProfileRequest dto);
    ProfileResponse toDto(ProfileModel model);
}