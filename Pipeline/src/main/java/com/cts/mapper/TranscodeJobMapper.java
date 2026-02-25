package com.cts.mapper;

import com.cts.dto.TranscodeJobRequestDTO;
import com.cts.dto.TranscodeJobResponseDTO;
import com.cts.model.TranscodeJob;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {TranscodeJob.TranscodeStatus.class,  java.time.LocalDate.class})
public interface TranscodeJobMapper {

    @Mapping(target = "transcodeId", ignore = true)
    @Mapping(target = "startedDate", source = "startedDate", defaultExpression = "java(LocalDate.now())")
    @Mapping(target = "completedDate", source = "completedDate", defaultExpression = "java(LocalDate.now())") // set later when job finishes
    @Mapping(target = "transcodeStatus", expression = "java(TranscodeStatus.Queued)") // default status
//    @Mapping(target = "asset", ignore = true) // resolved in service layer using assetId
    TranscodeJob toEntity(TranscodeJobRequestDTO dto);

//    @Mapping(target = "transcodeStatus", expression = "java(entity.getTranscodeStatus())")
//    @Mapping(target = "startedDate", expression = "java(entity.getStartedDate())")
//    @Mapping(target = "completedDate", expression = "java(entity.getCompletedDate())")
//    @Mapping(target = "assetId", source = "assetResponseDTO.assetId")
    TranscodeJobResponseDTO toDTO(TranscodeJob entity);
}
