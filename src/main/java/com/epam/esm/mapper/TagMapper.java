package com.epam.esm.mapper;

import com.epam.esm.dto.TagDto;
import com.epam.esm.model.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {

    TagDto toDto(Tag entity);

    Tag toEntity(TagDto dto);
}
