package com.epam.esm.mapper.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.Tag;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor_ = @Lazy)
public class TagMapperImpl implements TagMapper {
    @Lazy
    private final GiftCertificateMapper giftCertificateMapper;

    @Override
    public TagDto toDto(Tag entity) {
        if (entity == null) {
            return null;
        }

        return TagDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    @Override
    public Tag toEntity(TagDto dto) {
        if (dto == null) {
            return null;
        }

        return Tag.builder()
                .id(dto.getId())
                .name(dto.getName())
                .giftCertificateList(new ArrayList<>())
                .build();
    }
}
