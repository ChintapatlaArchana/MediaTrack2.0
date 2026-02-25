package com.cts.mapper;

import com.cts.dto.IngestJobRequestDTO;
import com.cts.dto.IngestJobResponseDTO;
import com.cts.model.IngestJob;
import com.cts.model.IngestJob.IngestStatus;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-25T09:46:14+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class IngestJobMapperImpl implements IngestJobMapper {

    @Override
    public IngestJob toEntity(IngestJobRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        IngestJob ingestJob = new IngestJob();

        ingestJob.setAssetId( dto.getAssetId() );
        ingestJob.setSourceUri( dto.getSourceUri() );

        ingestJob.setSubmittedDate( java.time.LocalDate );
        ingestJob.setIngestStatus( IngestStatus.Queued );

        return ingestJob;
    }

    @Override
    public IngestJobResponseDTO toDTO(IngestJob entity) {
        if ( entity == null ) {
            return null;
        }

        IngestJobResponseDTO ingestJobResponseDTO = new IngestJobResponseDTO();

        ingestJobResponseDTO.setIngestId( entity.getIngestId() );
        ingestJobResponseDTO.setAssetId( entity.getAssetId() );
        ingestJobResponseDTO.setSourceUri( entity.getSourceUri() );

        ingestJobResponseDTO.setIngestStatus( entity.getIngestStatus() );
        ingestJobResponseDTO.setSubmittedDate( entity.getSubmittedDate() );

        return ingestJobResponseDTO;
    }

    @Override
    public List<IngestJobResponseDTO> toDtoList(List<IngestJob> entities) {
        if ( entities == null ) {
            return null;
        }

        List<IngestJobResponseDTO> list = new ArrayList<IngestJobResponseDTO>( entities.size() );
        for ( IngestJob ingestJob : entities ) {
            list.add( toDTO( ingestJob ) );
        }

        return list;
    }
}
