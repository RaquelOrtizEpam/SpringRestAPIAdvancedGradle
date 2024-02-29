package com.epam.esm.services.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exceptions.TagNotFoundException;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.services.TagService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
                                id.toString()
                        ))
                );

        return tagMapper.toDto(tag);
    }

    @Override
    public TagDto getByTagName(String tagName) {
        Tag tag = tagRepository.findByName(tagName)
                .orElseThrow(() -> new TagNotFoundException(String.format(
                                tagName
                        ))
                );

        return tagMapper.toDto(tag);
    }

    @Override
    public void delete(TagDto tagDto) {
        Tag tag = tagRepository.findById(tagDto.getId()).orElse(null);

        if (tag != null) {
            tagRepository.delete(tag);
        }
    }

}
