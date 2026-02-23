package com.cts.mapper;

import com.cts.dto.CDNEndpointRequestDTO;
import com.cts.dto.CDNEndpointResponseDTO;
import com.cts.model.CDNEndpoint;
import com.cts.model.CDNEndpoint.Status;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-20T17:41:15+0530",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class CDNEndpointMapperImpl implements CDNEndpointMapper {

    @Override
    public CDNEndpoint toEntity(CDNEndpointRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CDNEndpoint cDNEndpoint = new CDNEndpoint();

        cDNEndpoint.setName( dto.getName() );
        cDNEndpoint.setBaseURL( dto.getBaseURL() );
        cDNEndpoint.setRegion( dto.getRegion() );

        cDNEndpoint.setStatus( Status.Active );

        return cDNEndpoint;
    }

    @Override
    public CDNEndpointResponseDTO toDto(CDNEndpoint entity) {
        if ( entity == null ) {
            return null;
        }

        CDNEndpointResponseDTO cDNEndpointResponseDTO = new CDNEndpointResponseDTO();

        cDNEndpointResponseDTO.setEndpointID( entity.getEndpointID() );
        cDNEndpointResponseDTO.setName( entity.getName() );
        cDNEndpointResponseDTO.setBaseURL( entity.getBaseURL() );
        cDNEndpointResponseDTO.setRegion( entity.getRegion() );

        cDNEndpointResponseDTO.setStatus( entity.getStatus() );

        return cDNEndpointResponseDTO;
    }
}
