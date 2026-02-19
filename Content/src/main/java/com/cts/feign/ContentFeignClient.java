package com.cts.feign;


import com.cts.dto.AssetResponseDTO;
import com.cts.dto.CategoryResponseDTO;
import com.cts.dto.TitleResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "content-service")
public interface ContentFeignClient {

    @GetMapping("/categories")
    List<CategoryResponseDTO> getCategories();

    @GetMapping("/titles")
    List<TitleResponseDTO> gettitles();

    @GetMapping("/titles/{id}")
    TitleResponseDTO getTitleById(@PathVariable("id") Long id);

    @GetMapping("/assets/{id}")
    AssetResponseDTO getAssetById(@PathVariable("id") Long id);
}
