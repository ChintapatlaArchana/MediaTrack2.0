package com.cts.mapper;

import com.cts.dto.TranscodeJobRequestDTO;
import com.cts.dto.TranscodeJobResponseDTO;
import com.cts.model.TranscodeJob;
import com.cts.model.TranscodeJob.TranscodeStatus;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-20T14:06:54+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class TranscodeJobMapperImpl implements TranscodeJobMapper {

    @Override
    public TranscodeJob toEntity(TranscodeJobRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        TranscodeJob transcodeJob = new TranscodeJob();

        transcodeJob.setAssetId( dto.getAssetId() );
        transcodeJob.setProfile( dto.getProfile() );

        transcodeJob.setStartedDate( java.time.LocalDateTime.now() );
        transcodeJob.setTranscodeStatus( TranscodeStatus.Queued );

        return transcodeJob;
    }

    @Override
    public TranscodeJobResponseDTO toDTO(TranscodeJob entity) {
        if ( entity == null ) {
            return null;
        }

        TranscodeJobResponseDTO transcodeJobResponseDTO = new TranscodeJobResponseDTO();

        transcodeJobResponseDTO.setTranscodeId( entity.getTranscodeId() );
        transcodeJobResponseDTO.setAssetId( entity.getAssetId() );
        transcodeJobResponseDTO.setProfile( entity.getProfile() );

        transcodeJobResponseDTO.setTranscodeStatus( entity.getTranscodeStatus() );
        transcodeJobResponseDTO.setStartedDate( entity.getStartedDate() );
        transcodeJobResponseDTO.setCompletedDate( entity.getCompletedDate() );

        return transcodeJobResponseDTO;
    }
}
