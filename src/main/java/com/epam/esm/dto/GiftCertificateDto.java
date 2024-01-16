package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GiftCertificateDto {
    @JsonView(Views.Summary.class)
    private Long id;
    @JsonView(Views.Summary.class)
    private String name;
    @JsonView(Views.Summary.class)
    private String description;
    @JsonView(Views.Summary.class)
    private Double price;
    @JsonView(Views.Summary.class)
    private String createDate;
    @JsonView(Views.Summary.class)
    private String lastUpdateDate;
    @JsonView(Views.Summary.class)
    private int durationInDays;
    @JsonView(Views.Details.class)
    private List<String> tags;
}
