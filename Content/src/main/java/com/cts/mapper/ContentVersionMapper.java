package com.cts.mapper;

import com.cts.dto.ContentVersionRequestDTO;
import com.cts.dto.ContentVersionResponseDTO;
import com.cts.model.ContentVersion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContentVersionMapper {
    //@Mapping(target = "versionLabel", expression = "java(ContentVersion.VersionLabel.valueOf(dto.getVersionLabel()))")
    ContentVersion toEntity(ContentVersionRequestDTO dto);

    @Mapping(target = "assetId",source = "asset.assetId")
    ContentVersionResponseDTO toDto(ContentVersion contentVersion);
    default ContentVersion.VersionLabel mapVersionLabel(String label) {
        return ContentVersion.VersionLabel.valueOf(label);
    }
    default String mapVersionLabel(ContentVersion.VersionLabel label) {
        return label.name();
    }
}
