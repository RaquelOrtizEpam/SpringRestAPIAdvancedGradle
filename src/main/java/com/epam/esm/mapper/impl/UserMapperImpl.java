package com.epam.esm.mapper.impl;

import com.epam.esm.dto.request.SignUpRequest;
import com.epam.esm.dto.response.RegisterResponse;
import com.epam.esm.dto.UserDto;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor_ = @Lazy)
public class UserMapperImpl implements UserMapper {
    @Override
    public UserDto toDto(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        return UserDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .build();
    }

    @Override
    public UserEntity toEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }

        return UserEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .build();
    }
    @Override
    public UserEntity requestToEntity(SignUpRequest dto) {
        if (dto == null) {
            return null;
        }

        return UserEntity.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .pass(dto.getPass())
                .build();
    }

    @Override
    public RegisterResponse toResponseDto(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        return RegisterResponse.builder()
                .name(entity.getName())
                .email(entity.getEmail())
                .build();
    }
}
