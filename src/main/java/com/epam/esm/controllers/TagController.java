package com.epam.esm.controllers;

import com.epam.esm.dto.TagDto;
import com.epam.esm.services.TagService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/tags")
@AllArgsConstructor
public class TagController {
    private final TagService tagService;

    @GetMapping("/{id}")
    public TagDto getById(@PathVariable Long id) {
        return tagService.getById(id);
    }

    @GetMapping("/name/{tagName}")
    public TagDto getByTagName(@PathVariable String tagName) {
        return tagService.getByTagName(tagName);
    }

    @PostMapping()
    public TagDto create(@RequestBody TagDto tagDto) {
        return tagService.create(tagDto);
    }

    @DeleteMapping()
    public void delete(@RequestBody TagDto tagDto) {
        tagService.delete(tagDto);
    }
}
