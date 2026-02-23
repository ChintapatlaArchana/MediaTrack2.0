// DRMEventMapper.java
package com.cts.mapper;

import com.cts.dto.DRMEventRequestDTO;
import com.cts.dto.DRMEventResponseDTO;
import com.cts.model.DRMEvent;
import com.cts.model.DRMEvent.LicenseStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DRMEventMapper {

    // Request DTO -> Entity (playbackSession is set in Service)
    @Mapping(target = "licenseStatus", expression = "java(toLicenseStatus(dto.getLicenseStatus()))")
    DRMEvent toEntity(DRMEventRequestDTO dto);

    // Partial update
    @Mapping(target = "licenseStatus", expression = "java(toLicenseStatus(dto.getLicenseStatus()))")
    void updateEntity(@MappingTarget DRMEvent entity, DRMEventRequestDTO dto);

    // Entity -> Response DTO (map relation id + enum->String)
    @Mapping(target = "playbackSessionId",
            expression = "java(entity.getPlaybackSession() != null ? entity.getPlaybackSession().getSessionId() : null)")
    @Mapping(target = "licenseStatus", expression = "java(fromLicenseStatus(entity.getLicenseStatus()))")
    DRMEventResponseDTO toDto(DRMEvent entity);

    // Helpers
    default LicenseStatus toLicenseStatus(String s) {
        if (s == null) return null;
        String normalized = s.trim();
        if (normalized.equalsIgnoreCase("granted")) return LicenseStatus.Granted;
        if (normalized.equalsIgnoreCase("denied"))  return LicenseStatus.Denied;
        if (normalized.equalsIgnoreCase("expired")) return LicenseStatus.Expired;
        // You can be strict and throw, or fallback:
        throw new IllegalArgumentException(
                "Invalid licenseStatus: '" + s + "'. Allowed: Granted, Denied, Expired"
        );
    }

    default String fromLicenseStatus(LicenseStatus status) {
        return status == null ? null : status.name(); // "Granted" / "Denied" / "Expired"
    }
}
