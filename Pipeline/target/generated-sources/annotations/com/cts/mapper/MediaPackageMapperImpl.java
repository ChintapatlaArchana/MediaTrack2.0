package com.cts.mapper;

import com.cts.dto.MediaPackageRequestDTO;
import com.cts.dto.MediaPackageResponseDTO;
import com.cts.model.MediaPackage;
import com.cts.model.MediaPackage.QCStatus;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-25T09:46:14+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class MediaPackageMapperImpl implements MediaPackageMapper {

    @Override
    public MediaPackage toEntity(MediaPackageRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        MediaPackage mediaPackage = new MediaPackage();

        mediaPackage.setAssetId( dto.getAssetId() );
        mediaPackage.setFormat( dto.getFormat() );
        mediaPackage.setDrm( dto.getDrm() );
        mediaPackage.setCdnPath( dto.getCdnPath() );

        mediaPackage.setQcStatus( QCStatus.Pending );

        return mediaPackage;
    }

    @Override
    public MediaPackageResponseDTO toDTO(MediaPackage entity) {
        if ( entity == null ) {
            return null;
        }

        MediaPackageResponseDTO mediaPackageResponseDTO = new MediaPackageResponseDTO();

        mediaPackageResponseDTO.setPackageId( entity.getPackageId() );
        mediaPackageResponseDTO.setAssetId( entity.getAssetId() );
        mediaPackageResponseDTO.setFormat( entity.getFormat() );
        mediaPackageResponseDTO.setDrm( entity.getDrm() );
        mediaPackageResponseDTO.setCdnPath( entity.getCdnPath() );

        mediaPackageResponseDTO.setQcStatus( entity.getQcStatus() );

        return mediaPackageResponseDTO;
    }
}
