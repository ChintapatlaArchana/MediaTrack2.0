package com.cts.service;

import com.cts.dto.CDNEndpointRequestDTO;
import com.cts.dto.CDNEndpointResponseDTO;
import com.cts.exception.GlobalException;
import com.cts.mapper.CDNEndpointMapper;
import com.cts.model.CDNEndpoint;
import com.cts.repository.CDNRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CDNService {

    private final CDNRepository cdnRepository;
    private final CDNEndpointMapper cdnEndpointMapper;

    public CDNService(CDNRepository cdnRepository, CDNEndpointMapper cdnEndpointMapper) {
        this.cdnRepository = cdnRepository;
        this.cdnEndpointMapper = cdnEndpointMapper;
    }

    public CDNEndpointResponseDTO create(CDNEndpointRequestDTO request) {
        try {
            CDNEndpoint entity = cdnEndpointMapper.toEntity(request);
            CDNEndpoint saved = cdnRepository.save(entity);
            return cdnEndpointMapper.toDto(saved);
        } catch (GlobalException ex) {
            throw new GlobalException("Error creating CDN endpoint: " + ex.getMessage());
        }
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public List<CDNEndpointResponseDTO> findAll() {
        try {
            return cdnRepository.findAll().stream()
                    .map(cdnEndpointMapper::toDto)
                    .toList();
        } catch (GlobalException ex) {
            throw new GlobalException("Error fetching CDN endpoints: " + ex.getMessage());
        }
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public CDNEndpointResponseDTO findById(Long id) {
        try {
            CDNEndpoint entity = cdnRepository.findById(id)
                    .orElseThrow(() -> new GlobalException("CDN endpoint not found with id: " + id));
            return cdnEndpointMapper.toDto(entity);
        } catch (GlobalException ex) {
            throw new GlobalException("Error fetching CDN endpoint: " + ex.getMessage());
        }
    }

    public CDNEndpointResponseDTO update(Long id, CDNEndpointRequestDTO request) {
        try {
            CDNEndpoint entity = cdnRepository.findById(id)
                    .orElseThrow(() -> new GlobalException("CDN endpoint not found with id: " + id));

            entity.setName(request.getName());
            entity.setBaseURL(request.getBaseURL());
            entity.setRegion(request.getRegion());
            entity.setStatus(request.getStatus());

            CDNEndpoint saved = cdnRepository.save(entity);
            return cdnEndpointMapper.toDto(saved);
        } catch (GlobalException ex) {
            throw new GlobalException("Error updating CDN endpoint: " + ex.getMessage());
        }
    }

    public void delete(Long id) {
        try {
            CDNEndpoint entity = cdnRepository.findById(id)
                    .orElseThrow(() -> new GlobalException("CDN endpoint not found with id: " + id));
            cdnRepository.delete(entity);
        } catch (GlobalException ex) {
            throw new GlobalException("Error deleting CDN endpoint: " + ex.getMessage());
        }
    }

    public CDNEndpointResponseDTO updateStatus(Long id, CDNEndpoint.Status status) {
        try {
            CDNEndpoint entity = cdnRepository.findById(id)
                    .orElseThrow(() -> new GlobalException("CDN endpoint not found with id: " + id));
            entity.setStatus(status);
            CDNEndpoint saved = cdnRepository.save(entity);
            return cdnEndpointMapper.toDto(saved);
        } catch (GlobalException ex) {
            throw new GlobalException("Error updating CDN endpoint status: " + ex.getMessage());
        }
    }
}