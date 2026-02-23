package com.cts.mapper;

import com.cts.dto.PlaybackSessionRequestDTO;
import com.cts.dto.PlaybackSessionResponseDTO;
import com.cts.model.PlaybackSession;
import com.cts.model.PlaybackSession.Status;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-23T14:17:38+0530",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class PlaybackSessionMapperImpl implements PlaybackSessionMapper {

    @Override
    public PlaybackSession toEntity(PlaybackSessionRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        PlaybackSession playbackSession = new PlaybackSession();

        playbackSession.setUserId( dto.getUserId() );
        playbackSession.setAssetId( dto.getAssetId() );
        playbackSession.setStartTime( dto.getStartTime() );
        playbackSession.setEndTime( dto.getEndTime() );
        if ( dto.getBitrateAvg() != null ) {
            playbackSession.setBitrateAvg( dto.getBitrateAvg() );
        }
        if ( dto.getBufferEvents() != null ) {
            playbackSession.setBufferEvents( dto.getBufferEvents() );
        }

        playbackSession.setStatus( Status.valueOf(dto.getStatus()) );

        return playbackSession;
    }

    @Override
    public void updateEntity(PlaybackSession entity, PlaybackSessionRequestDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getStatus() != null ) {
            entity.setStatus( Enum.valueOf( PlaybackSession.Status.class, dto.getStatus() ) );
        }
        else {
            entity.setStatus( null );
        }
    }

    @Override
    public PlaybackSessionResponseDTO toDto(PlaybackSession entity) {
        if ( entity == null ) {
            return null;
        }

        PlaybackSessionResponseDTO playbackSessionResponseDTO = new PlaybackSessionResponseDTO();

        playbackSessionResponseDTO.setUserId( entity.getUserId() );
        playbackSessionResponseDTO.setAssetId( entity.getAssetId() );
        playbackSessionResponseDTO.setSessionId( entity.getSessionId() );
        playbackSessionResponseDTO.setStartTime( entity.getStartTime() );
        playbackSessionResponseDTO.setEndTime( entity.getEndTime() );
        playbackSessionResponseDTO.setBitrateAvg( entity.getBitrateAvg() );
        playbackSessionResponseDTO.setBufferEvents( entity.getBufferEvents() );

        playbackSessionResponseDTO.setStatus( entity.getStatus() );

        return playbackSessionResponseDTO;
    }
}
