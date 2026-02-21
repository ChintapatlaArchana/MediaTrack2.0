package com.cts.dto;


import com.cts.model.Title.ApplicationStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class TitleResponseDTO{

    private Long titleId;
    private String name;
    private String synopsis;
    private String genre;
    private LocalDate releaseDate;
    private String rating;
    private ApplicationStatus applicationStatus;
    private Long categoryId;

    // private List<AssetResponseDTO> assetIds;
}
