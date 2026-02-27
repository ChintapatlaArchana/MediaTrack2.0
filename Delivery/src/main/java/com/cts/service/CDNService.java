package com.cts.service;

import com.cts.dto.CDNEndpointRequestDTO;
import com.cts.dto.CDNEndpointResponseDTO;
import com.cts.exception.GlobalException;
import com.cts.mapper.CDNEndpointMapper;
import com.cts.model.CDNEndpoint;
import com.cts.repository.CDNRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@Slf4j
public class CDNService {

    private final CDNRepository cdnRepository;
    private final CDNEndpointMapper cdnEndpointMapper;

    public CDNService(CDNRepository cdnRepository, CDNEndpointMapper cdnEndpointMapper) {
        this.cdnRepository = cdnRepository;
        this.cdnEndpointMapper = cdnEndpointMapper;
    }

    // Create
    public CDNEndpointResponseDTO create(CDNEndpointRequestDTO request) {
        log.info("Creating CDN endpoint with name: {}", request.getName());
        try {
            CDNEndpoint entity = cdnEndpointMapper.toEntity(request);
            CDNEndpoint saved = cdnRepository.save(entity);
            log.info("CDN endpoint created successfully with id: {}", saved.getEndpointID());
            return cdnEndpointMapper.toDto(saved);
        } catch (GlobalException ex) {
            log.error("Error creating CDN endpoint: {}", ex.getMessage());
            throw new GlobalException("Error creating CDN endpoint: " + ex.getMessage());
        }
    }

    // Read All
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<CDNEndpointResponseDTO> findAll() {
        List<CDNEndpoint> entities = cdnRepository.findAll();
        if (entities.isEmpty()) {
            log.error("No CDN endpoints found");
            throw new GlobalException("No CDN endpoints found");
        }
        log.info("Retrieved {} CDN endpoints", entities.size());
        return entities.stream().map(cdnEndpointMapper::toDto).toList();
    }

    // Read by ID
    @Transactional(Transactional.TxType.SUPPORTS)
    public CDNEndpointResponseDTO findById(Long id) {
        CDNEndpoint entity = cdnRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("CDN endpoint not found with id: {}", id);
                    return new GlobalException("CDN endpoint not found with id: " + id);
                });
        log.info("Found CDN endpoint with id: {}", id);
        return cdnEndpointMapper.toDto(entity);
    }

    // Update
    public CDNEndpointResponseDTO update(Long id, CDNEndpointRequestDTO request) {
        CDNEndpoint entity = cdnRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Cannot update. CDN endpoint not found with id: {}", id);
                    return new GlobalException("CDN endpoint not found with id: " + id);
                });

        log.info("Updating CDN endpoint id: {}", id);
        entity.setName(request.getName());
        entity.setBaseURL(request.getBaseURL());
        entity.setRegion(request.getRegion());
        entity.setStatus(request.getStatus());

        CDNEndpoint saved = cdnRepository.save(entity);
        log.info("CDN endpoint updated successfully with id: {}", id);
        return cdnEndpointMapper.toDto(saved);
    }

    // Delete
    public void delete(Long id) {
        CDNEndpoint entity = cdnRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Cannot delete. CDN endpoint not found with id: {}", id);
                    return new GlobalException("CDN endpoint not found with id: " + id);
                });

        cdnRepository.delete(entity);
        log.info("Deleted CDN endpoint with id: {}", id);
    }

    // Update Status
    public CDNEndpointResponseDTO updateStatus(Long id, CDNEndpoint.Status status) {
        CDNEndpoint entity = cdnRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Cannot update status. CDN endpoint not found with id: {}", id);
                    return new GlobalException("CDN endpoint not found with id: " + id);
                });

        log.info("Updating status for CDN endpoint id: {} to {}", id, status);
        entity.setStatus(status);
        CDNEndpoint saved = cdnRepository.save(entity);
        log.info("Status updated successfully for CDN endpoint id: {}", id);

        return cdnEndpointMapper.toDto(saved);
    }
}