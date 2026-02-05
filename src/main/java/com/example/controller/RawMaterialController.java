package com.example.controller;

import com.example.dto.RawMaterialRequestDTO;
import com.example.dto.RawMaterialResponseDTO;
import com.example.entity.RawMaterial;
import com.example.service.RawMaterialService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/raw-materials")
public class RawMaterialController {

    private final RawMaterialService rawMaterialService;

    public RawMaterialController(RawMaterialService rawMaterialService) {
        this.rawMaterialService = rawMaterialService;
    }


    @GetMapping
    public Page<RawMaterialResponseDTO> findAll(Pageable pageable) {
        return rawMaterialService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public RawMaterialResponseDTO findById(@PathVariable Long id) {
        return rawMaterialService.findById(id);
    }

    @PostMapping
    public RawMaterialResponseDTO create(@RequestBody RawMaterialRequestDTO rawMaterial) {
        return rawMaterialService.create(rawMaterial);
    }

    @PutMapping("/{id}")
    public RawMaterialResponseDTO update(@PathVariable Long id,
                              @RequestBody RawMaterialRequestDTO rawMaterial) {
        return rawMaterialService.update(id, rawMaterial);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        rawMaterialService.delete(id);
    }

}
