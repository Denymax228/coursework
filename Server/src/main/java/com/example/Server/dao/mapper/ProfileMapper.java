package com.example.Server.dao.mapper;

import com.example.Server.dao.entity.ProfileEntity;
import com.example.Server.domain.model.ProfileModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    ProfileModel toDomain(ProfileEntity entity);
    ProfileEntity toEntity(ProfileModel domain);
}