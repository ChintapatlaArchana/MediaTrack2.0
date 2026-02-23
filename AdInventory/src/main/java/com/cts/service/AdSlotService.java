//package com.cts.service.ads;
//
//import com.cts.model.AdSlot;
//import com.cts.repository.ads.AdSlotRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class AdSlotService {
//
//    @Autowired
//    private AdSlotRepository adSlotRepository;
//
//    public AdSlot addAdSlot(AdSlot ad) { return adSlotRepository.save(ad); }
//
//    public List<AdSlot> getAllAdSlots() { return adSlotRepository.findAll(); }
//}
//

package com.cts.service;

import com.cts.dto.AdSlotRequestDTO;
import com.cts.dto.AdSlotResponseDTO;
import com.cts.mapper.AdSlotMapper;
import com.cts.model.AdSlot;
import com.cts.repository.AdSlotRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdSlotService {

    private final AdSlotRepository adSlotRepository;
    private final AdSlotMapper adSlotMapper;

    public AdSlotService(AdSlotRepository repository, AdSlotMapper mapper) {
        this.adSlotRepository = repository;
        this.adSlotMapper = mapper;
    }

    // CREATE
    public AdSlotResponseDTO create(AdSlotRequestDTO dto) {
        AdSlot entity = adSlotMapper.toEntity(dto);
        return adSlotMapper.toDTO(adSlotRepository.save(entity));
    }

    // GET ALL
    public List<AdSlotResponseDTO> getAll() {
        return adSlotRepository.findAll()
                .stream()
                .map(adSlotMapper::toDTO)
                .collect(Collectors.toList());
    }

    // GET BY ID
    public AdSlotResponseDTO getById(Long id) {
        AdSlot entity = adSlotRepository.findById(id).orElseThrow(
                () -> new RuntimeException("AdSlot not found: " + id)
        );
        return adSlotMapper.toDTO(entity);
    }

    // UPDATE
    public AdSlotResponseDTO update(Long id, AdSlotRequestDTO dto) {
        AdSlot existing = adSlotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AdSlot not found: " + id));

        existing.setPlacement(AdSlot.Placement.valueOf(dto.getPlacement()));
        existing.setDuration(dto.getDuration());
        existing.setConstraintsJson(String.valueOf(dto.getConstraintsJson()));

        return adSlotMapper.toDTO(adSlotRepository.save(existing));
    }

    // DELETE
    public String delete(Long id) {
        adSlotRepository.deleteById(id);
        return "AdSlot deleted successfully.";
    }
}