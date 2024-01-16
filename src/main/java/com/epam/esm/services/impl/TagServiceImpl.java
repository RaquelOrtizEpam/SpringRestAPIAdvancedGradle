package com.epam.esm.services.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exceptions.TagNotFoundException;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.services.TagService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.epam.esm.controllers.advice.entity.ErrorsCodes.TAG_NOT_FOUND_BY_ID;
import static com.epam.esm.controllers.advice.entity.ErrorsCodes.TAG_NOT_FOUND_BY_NAME;

@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    public TagDto create(TagDto tagDto) {
        Tag tag = tagRepository.create(tagMapper.toEntity(tagDto));

        return tagMapper.toDto(tag);
    }

    @Override
    public TagDto getById(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new TagNotFoundException(String.format(
                                TAG_NOT_FOUND_BY_ID.getMessage(),
                                id.toString()
                        ))
                );

        return tagMapper.toDto(tag);
    }

    @Override
    public TagDto getByTagName(String tagName) {
        Tag tag = tagRepository.findByName(tagName)
                .orElseThrow(() -> new TagNotFoundException(String.format(
                                TAG_NOT_FOUND_BY_NAME.getMessage(),
                                tagName
                        ))
                );

        return tagMapper.toDto(tag);
    }

    @Override
    public void delete(TagDto tagDto) {
        Tag tag = tagRepository.findById(tagDto.getId())
                .orElseThrow(() -> new TagNotFoundException(String.format(
                        TAG_NOT_FOUND_BY_ID.getMessage(),
                        tagDto.getId()
                )));

        tagRepository.delete(tag);
    }
}
