package com.epam.esm.services;

import com.epam.esm.dto.GiftCertificateDto;

import java.util.List;

public interface GiftCertificateService {

    GiftCertificateDto create(GiftCertificateDto giftCertificate);

    GiftCertificateDto getById(Long id);

    List<GiftCertificateDto> getAllByPartOfNameOrDescription(String string);

    List<GiftCertificateDto> getAllByTagName(String tagName);

    List<GiftCertificateDto> getGiftCertificatesWithTags(String tagName, String searchQuery, String sortBy, String sortType);

    GiftCertificateDto update(GiftCertificateDto giftCertificate);

    void delete(GiftCertificateDto giftCertificate);

    GiftCertificateDto updateField(Long id, String fieldName, Object value);

}
