package com.cts.dto;

import com.cts.model.Title;
import lombok.Data;

import java.util.List;

@Data
public class CategoryRequestDTO {
    //private Long categoryId;
    private String name;
    private String description;
    //private List<Title> title;
}
