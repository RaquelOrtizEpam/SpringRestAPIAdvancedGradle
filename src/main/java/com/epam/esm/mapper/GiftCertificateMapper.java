package com.epam.esm.mapper;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.model.GiftCertificate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GiftCertificateMapper {

    GiftCertificateDto toDto(GiftCertificate entity);

    GiftCertificate toEntity(GiftCertificateDto dto);
}
