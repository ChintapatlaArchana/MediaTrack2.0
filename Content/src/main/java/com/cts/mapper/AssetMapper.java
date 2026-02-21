package com.cts.mapper;

import com.cts.dto.AssetRequestDTO;
import com.cts.dto.AssetResponseDTO;
import com.cts.model.Asset;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AssetMapper {


    Asset toEntity(AssetRequestDTO dto);
    @Mapping(target = "titleId",source = "title.titleId")
    AssetResponseDTO toDto(Asset asset);

    default Asset.AssetType mapAssetType(String type) {
        return type == null ? null : Asset.AssetType.valueOf(type.toUpperCase());
    }

    default String mapAssetType(Asset.AssetType type) {
        return type == null ? null : type.name();
    }
}
