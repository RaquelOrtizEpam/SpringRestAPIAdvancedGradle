package com.epam.esm.services;

import com.epam.esm.dto.TagDto;

public interface TagService {

    TagDto create(TagDto tagDto);

    TagDto getById(Long id);

    TagDto getByTagName(String tagName);

    void delete(TagDto tagDto);
}
