package com.cts.service;

import com.cts.dto.CDNEndpointRequestDTO;
import com.cts.dto.CDNEndpointResponseDTO;
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
        CDNEndpoint entity = cdnEndpointMapper.toEntity(request);
        CDNEndpoint saved = cdnRepository.save(entity);
        return cdnEndpointMapper.toDto(saved);
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public List<CDNEndpointResponseDTO> findAll() {
        return cdnRepository.findAll()
                .stream()
                .map(cdnEndpointMapper::toDto)
                .toList();
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public CDNEndpointResponseDTO findById(Long id) {
        CDNEndpoint entity = cdnRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CDNEndpoint not found: " + id));
        return cdnEndpointMapper.toDto(entity);
    }

    public CDNEndpointResponseDTO update(Long id, CDNEndpointRequestDTO request) {
        CDNEndpoint entity = cdnRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CDNEndpoint not found: " + id));

        entity.setName(request.getName());
        entity.setBaseURL(request.getBaseURL());
        entity.setRegion(request.getRegion());
        entity.setStatus(request.getStatus());

        CDNEndpoint saved = cdnRepository.save(entity);
        return cdnEndpointMapper.toDto(saved);
    }

    public void delete(Long id) {
        CDNEndpoint entity = cdnRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CDNEndpoint not found: " + id));
        cdnRepository.delete(entity);
    }

    public CDNEndpointResponseDTO updateStatus(Long id, CDNEndpoint.Status status) {
        CDNEndpoint entity = cdnRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CDNEndpoint not found: " + id));

        entity.setStatus(status);
        CDNEndpoint saved = cdnRepository.save(entity);

        return cdnEndpointMapper.toDto(saved);
    }
}

