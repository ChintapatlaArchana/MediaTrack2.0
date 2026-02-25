

package com.cts.mapper;

import com.cts.dto.IngestJobRequestDTO;
import com.cts.dto.IngestJobResponseDTO;
import com.cts.model.IngestJob;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", imports = {IngestJob.IngestStatus.class, java.time.LocalDate.class})
public interface IngestJobMapper {

    @Mapping(target = "ingestId", ignore = true)
    @Mapping(target = "submittedDate", source = "submittedDate", defaultExpression = "java(LocalDate.now())")
    @Mapping(target = "ingestStatus", expression = "java(IngestStatus.Queued)") // default status
//    @Mapping(target = "asset", ignore = true) // handled in service layer
    IngestJob toEntity(IngestJobRequestDTO dto);

    @Mapping(target = "ingestStatus", expression = "java(entity.getIngestStatus())")
    @Mapping(target = "submittedDate", expression = "java(entity.getSubmittedDate())")
//    @Mapping(target = "assetId", source = "assetResponseDTO.assetId")
    IngestJobResponseDTO toDTO(IngestJob entity);

    //return in list
    List<com.cts.dto.IngestJobResponseDTO> toDtoList(List<IngestJob> entities);
//    List<IngestJob> toEntityList(List<IngestJobRequestDTO> dtos);
}

