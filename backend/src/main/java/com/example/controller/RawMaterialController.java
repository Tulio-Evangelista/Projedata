package com.example.controller;

import com.example.dto.RawMaterialRequestDTO;
import com.example.dto.RawMaterialResponseDTO;
import com.example.entity.RawMaterial;
import com.example.service.RawMaterialService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/raw-materials")
public class RawMaterialController {

    private final RawMaterialService rawMaterialService;

    public RawMaterialController(RawMaterialService rawMaterialService) {
        this.rawMaterialService = rawMaterialService;
    }

    @Operation(summary = "list all raw materials")
    @GetMapping
    public Page<RawMaterialResponseDTO> findAll(Pageable pageable) {
        return rawMaterialService.findAll(pageable);
    }

    @Operation(summary = "find raw material by id")
    @GetMapping("/{id}")
    public RawMaterialResponseDTO findById(@PathVariable Long id) {
        return rawMaterialService.findById(id);
    }

    @Operation(summary = "create new raw material")
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

    @PatchMapping("/raw-materials/{id}/adjust")
    public RawMaterial adjustRawMaterialStock(
            @PathVariable Long id,
            @RequestParam int quantity) {
        return rawMaterialService.adjustStock(id, quantity);
    }


}
