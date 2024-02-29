package com.epam.esm.mapper;

import com.epam.esm.dto.request.SignUpRequest;
import com.epam.esm.dto.response.RegisterResponse;
import com.epam.esm.dto.UserDto;
import com.epam.esm.model.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(UserEntity entity);

    UserEntity toEntity(UserDto dto);

    RegisterResponse toResponseDto(UserEntity entity);

    UserEntity requestToEntity(SignUpRequest dto);

}