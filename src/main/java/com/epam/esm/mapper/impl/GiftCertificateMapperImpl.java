package com.epam.esm.mapper.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.GiftCertificate;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(onConstructor_ = @Lazy)
public class GiftCertificateMapperImpl implements GiftCertificateMapper {
    @Lazy
    private final TagMapper tagMapper;

    @Override
    public GiftCertificateDto toDto(GiftCertificate entity) {
        if (entity == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SS");

        return GiftCertificateDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .createDate(entity.getCreateDate().format(formatter))
                .lastUpdateDate(entity.getLastUpdateDate().format(formatter))
                .durationInDays(entity.getDurationInDays())
                .tags(
                        Optional.ofNullable(entity.getTagList())
                                .orElse(Collections.emptyList())
                                .stream()
                                .map(tagMapper::toDto)
                                .map(TagDto::getName)
                                .toList()
                )
                .build();
    }

    @Override
    public GiftCertificate toEntity(GiftCertificateDto dto) {
        if (dto == null) {
            return null;
        }

        return GiftCertificate.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .durationInDays(dto.getDurationInDays())
                .tagList(new ArrayList<>())
                .build();
    }
}
