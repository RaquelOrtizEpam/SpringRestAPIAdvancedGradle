package com.epam.esm.controllers;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.Views;
import com.epam.esm.services.GiftCertificateService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gift-certificates")
@AllArgsConstructor
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;

    @GetMapping("/search")
    public List<GiftCertificateDto> search(
            @RequestParam(required = false) String tagName,
            @RequestParam(required = false) String searchQuery,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortType) {

        return giftCertificateService.getGiftCertificatesWithTags(tagName, searchQuery, sortBy, sortType);
    }

    @GetMapping("/search/{searchQuery}")
    public List<GiftCertificateDto> searchByQuery(@PathVariable String searchQuery) {
        return giftCertificateService.getAllByPartOfNameOrDescription(searchQuery);
    }

    @GetMapping("/{id}")
    public GiftCertificateDto getById(@PathVariable Long id) {
        return giftCertificateService.getById(id);
    }

    @GetMapping("/tag/{tagName}")
    @JsonView(Views.Summary.class)
    public List<GiftCertificateDto> getByTagName(@PathVariable String tagName) {
        return giftCertificateService.getAllByTagName(tagName);
    }

    @PostMapping
    public GiftCertificateDto create(@RequestBody GiftCertificateDto giftCertificateDto) {
        return giftCertificateService.create(giftCertificateDto);
    }

    @PutMapping
    public GiftCertificateDto update(@RequestBody GiftCertificateDto giftCertificateDto) {
        return giftCertificateService.update(giftCertificateDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestBody GiftCertificateDto giftCertificateDto) {
        giftCertificateService.delete(giftCertificateDto);
    }
}
