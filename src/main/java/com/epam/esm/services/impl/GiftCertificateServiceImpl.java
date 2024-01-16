package com.epam.esm.services.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exceptions.GiftCertificationNotFoundException;
import com.epam.esm.exceptions.TagNotFoundException;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.services.GiftCertificateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.epam.esm.controllers.advice.entity.ErrorsCodes.GIFT_CERTIFICATE_NOT_FOUND;
import static com.epam.esm.controllers.advice.entity.ErrorsCodes.TAG_NOT_FOUND_BY_NAME;

@Service
@AllArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository giftCertificateRepository;
    private final TagRepository tagRepository;
    private final GiftCertificateMapper giftCertificateMapper;

    @Override
    @Transactional
    public GiftCertificateDto create(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate =
                giftCertificateRepository.create(giftCertificateMapper.toEntity(giftCertificateDto));

        List<Tag> tags = giftCertificateDto.getTags().stream()
                .map(tagName -> tagRepository.findByName(tagName)
                        .orElse(tagRepository.create(new Tag(tagName))))
                .toList();

        giftCertificate.setTagList(tags);
        giftCertificateRepository.associateTags(giftCertificate.getId(), tags);

        return giftCertificateMapper.toDto(giftCertificate);
    }

    @Override
    public GiftCertificateDto getById(Long id) {
        GiftCertificate giftCertificate = giftCertificateRepository.findById(id)
                .orElseThrow(() -> new GiftCertificationNotFoundException(
                        String.format(
                                GIFT_CERTIFICATE_NOT_FOUND.getMessage(),
                                id.toString()
                        )
                ));

        List<Tag> tagList = tagRepository.findAllByGiftCertificateId(id);
        giftCertificate.setTagList(tagList);

        return giftCertificateMapper.toDto(giftCertificate);
    }

    @Override
    public List<GiftCertificateDto> getAllByPartOfNameOrDescription(String string) {
        List<GiftCertificate> certificateList = giftCertificateRepository.searchByPartOfNameOrDescription(string);
        certificateList.forEach(giftCertificate -> {
            List<Tag> tags = tagRepository.findAllByGiftCertificateId(giftCertificate.getId());
            giftCertificate.setTagList(tags);
        });

        return certificateList.stream()
                .map(giftCertificateMapper::toDto)
                .toList();
    }

    @Override
    public List<GiftCertificateDto> getAllByTagName(String tagName) {
        Tag tag = tagRepository.findByName(tagName)
                .orElseThrow(() -> new TagNotFoundException(
                        String.format(
                                TAG_NOT_FOUND_BY_NAME.getMessage(),
                                tagName
                        )
                ));
        return giftCertificateRepository.findAllByTag(tag).stream()
                .map(giftCertificateMapper::toDto)
                .toList();
    }

    @Override
    public List<GiftCertificateDto> getGiftCertificatesWithTags(String tagName, String searchQuery, String sortBy, String sortType) {
        List<GiftCertificate> giftCertificates = giftCertificateRepository.findGiftCertificatesWithTags(tagName, searchQuery, sortBy, sortType);

        return giftCertificates.stream()
                .map(giftCertificate -> {
                    List<Tag> tags = tagRepository.findAllByGiftCertificateId(giftCertificate.getId());
                    giftCertificate.setTagList(tags);
                    return giftCertificateMapper.toDto(giftCertificate);
                })
                .toList();
    }

    @Override
    @Transactional
    public GiftCertificateDto update(GiftCertificateDto giftCertificateDto) {
        GiftCertificate existingGiftCertificate = giftCertificateRepository.findById(giftCertificateDto.getId())
                .orElseThrow(() -> new GiftCertificationNotFoundException(
                        String.format(
                                GIFT_CERTIFICATE_NOT_FOUND.getMessage(),
                                giftCertificateDto.getId().toString()
                        )
                ));

        existingGiftCertificate.setName(giftCertificateDto.getName());
        existingGiftCertificate.setDescription(giftCertificateDto.getDescription());
        existingGiftCertificate.setPrice(giftCertificateDto.getPrice());
        existingGiftCertificate.setDurationInDays(giftCertificateDto.getDurationInDays());

        List<Tag> existingTags = tagRepository.findAllByGiftCertificateId(existingGiftCertificate.getId());
        List<String> newTags = giftCertificateDto.getTags();

        List<Tag> tagsToRemove = existingTags.stream()
                .filter(tag -> !newTags.contains(tag.getName()))
                .toList();
        List<Tag> tagsToAdd = newTags.stream()
                .map(tagName -> tagRepository.create(new Tag(tagName)))
                .toList();

        giftCertificateRepository.disassociateTags(existingGiftCertificate.getId(), tagsToRemove);
        giftCertificateRepository.associateTags(existingGiftCertificate.getId(), tagsToAdd);
        giftCertificateRepository.update(existingGiftCertificate);

        existingGiftCertificate.setTagList(tagsToAdd);

        return giftCertificateMapper.toDto(existingGiftCertificate);
    }

    @Override
    @Transactional
    public void delete(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = giftCertificateRepository.findById(giftCertificateDto.getId())
                .orElseThrow(() -> new GiftCertificationNotFoundException(
                        String.format(
                                GIFT_CERTIFICATE_NOT_FOUND.getMessage(),
                                giftCertificateDto.getId().toString()
                        )
                ));
        List<Tag> tagsToRemove = tagRepository.findAllByGiftCertificateId(giftCertificateDto.getId());

        giftCertificateRepository.disassociateTags(giftCertificateDto.getId(), tagsToRemove);
        giftCertificateRepository.delete(giftCertificate);
    }
}
