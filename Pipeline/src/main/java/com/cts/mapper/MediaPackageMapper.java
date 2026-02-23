package com.cts.mapper;

import com.cts.dto.MediaPackageRequestDTO;
import com.cts.dto.MediaPackageResponseDTO;
import com.cts.model.MediaPackage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {MediaPackage.QCStatus.class})
public interface MediaPackageMapper {

    @Mapping(target = "packageId", ignore = true)
    @Mapping(target = "qcStatus", expression = "java(QCStatus.Pending)") // default QC status
//    @Mapping(target = "asset", ignore = true) // resolved in service layer using assetId
    MediaPackage toEntity(MediaPackageRequestDTO dto);

    @Mapping(target = "qcStatus", expression = "java(entity.getQcStatus())")
//    @Mapping(target = "assetId", source = "assetResponseDTO.assetId")
    MediaPackageResponseDTO toDTO(MediaPackage entity);
}
