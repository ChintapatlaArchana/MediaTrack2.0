package com.cts.dto;

import lombok.Data;

import java.util.List;
@Data
public class CategoryResponseDTO {
    private Long categoryId;
    private String name;
    private String description;

    //private List<TitleResponseDTO> titleIds;
}
