package com.cts.service;

import com.cts.dto.CreativeRequestDTO;
import com.cts.dto.CreativeResponseDTO;
import com.cts.mapper.CreativeMapper;
import com.cts.model.Creative;
import com.cts.repository.CreativeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreativeService {

    private final CreativeRepository repo;
    private final CreativeMapper mapper;

    public CreativeService(CreativeRepository repo, CreativeMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    // CREATE
    public CreativeResponseDTO create(CreativeRequestDTO dto) {
        Creative entity = mapper.toEntity(dto);
        entity.setStatus(Creative.Status.Active); // default added
        return mapper.toDTO(repo.save(entity));
    }

    // GET ALL
    public List<CreativeResponseDTO> getAll() {
        return repo.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    // GET BY ID
    public CreativeResponseDTO getById(Long id) {
        Creative entity = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Creative not found: " + id));
        return mapper.toDTO(entity);
    }

    // UPDATE
    public CreativeResponseDTO update(Long id, CreativeRequestDTO dto) {
        Creative existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Creative not found: " + id));

        existing.setAdvertiser(dto.getAdvertiser());
        existing.setMediaUri(dto.getMediaUri());
        existing.setDuration(dto.getDuration());
        existing.setClickThroughUrl(dto.getClickThroughUrl());

        return mapper.toDTO(repo.save(existing));
    }

    // DELETE
    public String delete(Long id) {
        repo.deleteById(id);
        return "Creative deleted successfully";
    }
}