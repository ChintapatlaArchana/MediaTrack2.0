package com.cts.mapper;


import com.cts.dto.TitleRequestDTO;
import com.cts.dto.TitleResponseDTO;
import com.cts.model.Title;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TitleMapper {


    // @Mapping(target = "applicationStatus",expression = "java(Title.ApplicationStatus.valueOf(dto.getApplicationStatus()))")
    Title toEntity(TitleRequestDTO dto);

    @Mapping(target = "categoryId", source = "category.categoryId")
    TitleResponseDTO toDto(Title title);
    default Title.ApplicationStatus mapApplicationStatus(String status) { return Title.ApplicationStatus.valueOf(status); } default String mapApplicationStatus(Title.ApplicationStatus status) { return status.name();}
}
