package com.cts.test.mapperTest;


import com.cts.dto.ContentVersionRequestDTO;
import com.cts.dto.ContentVersionResponseDTO;
import com.cts.mapper.ContentVersionMapper;
import com.cts.mapper.ContentVersionMapperImpl;
import com.cts.model.Asset;
import com.cts.model.ContentVersion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


class ContentVersionMapperTest {


    private ContentVersionMapper mapper;

    @BeforeEach
    void setUp(){
        mapper = new ContentVersionMapperImpl();
    }

    @Test
    void testMapVersionLabelFromString() {
        ContentVersion.VersionLabel label = mapper.mapVersionLabel("localized");
        assertEquals(ContentVersion.VersionLabel.localized, label);
    }

    @Test
    void testMapVersionLabelToString() {
        String label = mapper.mapVersionLabel(ContentVersion.VersionLabel.directorCut);
        assertEquals("directorCut", label);
    }

    @Test
    void testEntityToDtoMapping() {
        Asset asset = new Asset();
        asset.setAssetId(5L);

        ContentVersion version = new ContentVersion();
        version.setVersionId(10L);
        version.setNotes("Director's cut");
        version.setVersionLabel(ContentVersion.VersionLabel.directorCut);
        version.setAsset(asset);

        ContentVersionResponseDTO dto = mapper.toDto(version);

        assertEquals(10L, dto.getVersionId());
        assertEquals(5L, dto.getAssetId());
        assertEquals("Director's cut", dto.getNotes());
        assertEquals(ContentVersion.VersionLabel.directorCut, dto.getVersionLabel());
    }

    @Test
    void testDtoToEntityMapping() {
        ContentVersionRequestDTO dto = new ContentVersionRequestDTO();
        dto.setAssetId(7L);
        dto.setNotes("Localized version");
        dto.setVersionLabel(ContentVersion.VersionLabel.localized);

        ContentVersion entity = mapper.toEntity(dto);

        assertEquals("Localized version", entity.getNotes());
        assertEquals(ContentVersion.VersionLabel.localized, entity.getVersionLabel());
    }
}

