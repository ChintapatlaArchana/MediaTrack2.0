package com.cts.service;

import com.cts.dto.TitleRequestDTO;
import com.cts.dto.TitleResponseDTO;
import com.cts.exception.GlobalException;
import com.cts.mapper.TitleMapper;
import com.cts.model.Category;
import com.cts.model.Title;
import com.cts.repository.CategoryRepository;
import com.cts.repository.TitleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TitleService {

    private final CategoryRepository categoryRepository;
    private final TitleRepository titleRepository;
    private final TitleMapper titleMapper;

    public TitleService(CategoryRepository categoryRepository, TitleRepository titleRepository, TitleMapper titleMapper) {
        this.categoryRepository = categoryRepository;
        this.titleRepository = titleRepository;
        this.titleMapper = titleMapper;
    }

    public TitleResponseDTO createTitle(TitleRequestDTO dto) {
        Title title = titleMapper.toEntity(dto);

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new GlobalException("Category not found with id: " + dto.getCategoryId()));
        title.setCategory(category);

        return titleMapper.toDto(titleRepository.save(title));
    }

    public TitleResponseDTO updateTitle(Long titleId, TitleRequestDTO dto) {
        Title title = titleRepository.findById(titleId)
                .orElseThrow(() -> new GlobalException("Title not found with id: " + titleId));

        Title updated = titleMapper.toEntity(dto);
        updated.setTitleId(title.getTitleId());

        return titleMapper.toDto(titleRepository.save(updated));
    }

    public List<TitleResponseDTO> getAllTitles() {
        return titleRepository.findAll()
                .stream()
                .map(titleMapper::toDto)
                .toList();
    }

    public TitleResponseDTO getTitle(Long titleID) {
        Title title = titleRepository.findById(titleID)
                .orElseThrow(() -> new GlobalException("Title not found with id: " + titleID));
        return titleMapper.toDto(title);
    }

    public void deleteTitle(Long id) {
        if (!titleRepository.existsById(id)) {
            throw new GlobalException("Title not found with id: " + id);
        }
        titleRepository.deleteById(id);
    }
}
