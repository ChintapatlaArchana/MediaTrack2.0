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

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Map<Title.ApplicationStatus, Long> getCountByStatus() {
        List<Object[]> results = titleRepository.countByStatus();

        // Convert List of Object arrays to a Map
        return results.stream().collect(Collectors.toMap(
                result -> (Title.ApplicationStatus) result[0],
                result -> (Long) result[1]
        ));
    }
    public Title updateTitle(Long id, String name, String genre, LocalDate releaseDate) {
        Optional<Title> optionalTitle = titleRepository.findById(id);
        if (optionalTitle.isEmpty()) {
            throw new RuntimeException("Title not found with id: " + id);
        }

        Title title = optionalTitle.get();
        if (name != null) {
            title.setName(name);
        }
        if (genre != null) {
            title.setGenre(genre);
        }
        if (releaseDate != null) {
            title.setReleaseDate(releaseDate);
        }

        return titleRepository.save(title);
    }
}
