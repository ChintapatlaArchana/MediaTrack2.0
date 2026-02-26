package com.cts.mapper;

import com.cts.dto.ChurnCohortRequestDTO;
import com.cts.dto.ChurnCohortResponseDTO;
import com.cts.model.ChurnCohort;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-26T10:15:12+0530",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class ChurnCohortMapperImpl implements ChurnCohortMapper {

    @Override
    public ChurnCohort toEntity(ChurnCohortRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ChurnCohort churnCohort = new ChurnCohort();

        churnCohort.setPlanId( dto.getPlanId() );
        churnCohort.setStartPeriod( dto.getStartPeriod() );
        churnCohort.setRetainedPct( dto.getRetainedPct() );
        churnCohort.setChurnedPct( dto.getChurnedPct() );

        return churnCohort;
    }

    @Override
    public ChurnCohortResponseDTO toDto(ChurnCohort churnCohort) {
        if ( churnCohort == null ) {
            return null;
        }

        ChurnCohortResponseDTO churnCohortResponseDTO = new ChurnCohortResponseDTO();

        churnCohortResponseDTO.setPlanId( churnCohort.getPlanId() );
        churnCohortResponseDTO.setCohortId( churnCohort.getCohortId() );
        churnCohortResponseDTO.setStartPeriod( churnCohort.getStartPeriod() );
        churnCohortResponseDTO.setRetainedPct( churnCohort.getRetainedPct() );
        churnCohortResponseDTO.setChurnedPct( churnCohort.getChurnedPct() );

        return churnCohortResponseDTO;
    }
}
