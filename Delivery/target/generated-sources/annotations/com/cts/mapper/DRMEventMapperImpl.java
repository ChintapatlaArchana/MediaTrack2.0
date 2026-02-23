package com.cts.mapper;

import com.cts.dto.DRMEventRequestDTO;
import com.cts.dto.DRMEventResponseDTO;
import com.cts.model.DRMEvent;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-20T17:41:15+0530",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class DRMEventMapperImpl implements DRMEventMapper {

    @Override
    public DRMEvent toEntity(DRMEventRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        DRMEvent dRMEvent = new DRMEvent();

        dRMEvent.setDrmType( dto.getDrmType() );
        dRMEvent.setEventTime( dto.getEventTime() );

        dRMEvent.setLicenseStatus( toLicenseStatus(dto.getLicenseStatus()) );

        return dRMEvent;
    }

    @Override
    public void updateEntity(DRMEvent entity, DRMEventRequestDTO dto) {
        if ( dto == null ) {
            return;
        }

        entity.setDrmType( dto.getDrmType() );
        entity.setEventTime( dto.getEventTime() );

        entity.setLicenseStatus( toLicenseStatus(dto.getLicenseStatus()) );
    }

    @Override
    public DRMEventResponseDTO toDto(DRMEvent entity) {
        if ( entity == null ) {
            return null;
        }

        DRMEventResponseDTO dRMEventResponseDTO = new DRMEventResponseDTO();

        dRMEventResponseDTO.setDrmEventID( entity.getDrmEventID() );
        dRMEventResponseDTO.setDrmType( entity.getDrmType() );
        dRMEventResponseDTO.setEventTime( entity.getEventTime() );

        dRMEventResponseDTO.setPlaybackSessionId( entity.getPlaybackSession() != null ? entity.getPlaybackSession().getSessionId() : null );
        dRMEventResponseDTO.setLicenseStatus( fromLicenseStatus(entity.getLicenseStatus()) );

        return dRMEventResponseDTO;
    }
}
